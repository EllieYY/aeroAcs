package com.wim.aero.acs.message;

import com.wim.aero.acs.config.Constants;
import com.wim.aero.acs.util.ProtocolFiledUtil.CmdPropParam;
import com.wim.aero.acs.util.ProtocolFiledUtil.CmdProp;
import com.wim.aero.acs.util.ProtocolFiledUtil.FieldParser;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @title: Message
 * @author: Ellie
 * @date: 2022/02/10 11:28
 * @description:
 **/
@Data
@Slf4j
public abstract class Message<T extends MessageBody> {

    private static final String MSG_SPLITTER = " ";
    private static final int MSG_LENGTH_FIELD_LENGTH = 4;
    private static final int MSG_TYPE_SPLITTER = 1000;

    private MessageHeader messageHeader;
    private T messageBody;

    public T getMessageBody(){
        return messageBody;
    }

    public String encode() {
        // 报文体编码
        Class cls = messageBody.getClass();
        List<CmdPropParam> sParams = getSortedParams(cls);
        String bodyStr = encodeMsgBody(sParams);
        String messageStr = messageHeader.getMsgType() + MSG_SPLITTER + bodyStr;
        return messageStr;
    }

    private String encodeMsgBody(List<CmdPropParam> sParams) {
        StringBuilder retSb = new StringBuilder();
        String lastField = null;
        for (CmdPropParam sParam : sParams) {
            Field field = sParam.getField();
            field.setAccessible(true);
            String fieldVal = null;
            try {
                Object fieldValObj = field.get(messageBody);
                String codecMethod = sParam.getCodec();

                // 空值处理
                fieldValObj = Optional.ofNullable(fieldValObj).orElse(sParam.getDefaultVal());

                fieldVal = (String)codec(fieldValObj, codecMethod, field.getType());
                fieldVal = (fieldVal == null) ? "" : fieldVal;

            } catch (IllegalAccessException e) {
                log.error("属性{}访问异常，消息编码失败", field.getName(), e);
                return null;
            }

            if (StringUtils.hasText(fieldVal) && StringUtils.hasText(lastField)) {
                retSb.append(lastField).append(MSG_SPLITTER);
            }
            lastField = fieldVal;
        }
        if (lastField != null) {
            retSb.append(lastField);
        }

        return retSb.toString();
    }

    public abstract Class<T> getMessageBodyDecodeClass(int opcode);

    public boolean decode(String msg, String channelIp, String targetIp) {
//        String contextStr = msg.toString(CharsetUtil.UTF_8);
//
//        ByteBuf msgTypeBuf = msg.readBytes(4);
//        msg.skipBytes(1);
//        ByteBuf msgLengthBuf = msg.readBytes(4);
//        msg.skipBytes(1);
//
//        String msgTypeStr = msgTypeBuf.toString(CharsetUtil.UTF_8);
//        int msgType = Integer.parseInt(msgTypeStr);
//        if (!OperationType.isProtocolOpCode(msgType)) {
//            return false;
//        }
//
//        String lengthStr = msgLengthBuf.toString(StandardCharsets.UTF_8);
//
//        msgTypeBuf.release();
//        msgLengthBuf.release();
//
//        String msgConetxStr = msg.toString(Charset.forName("UTF-8"));
////        String targetIp = decodeTargetIp(msgType, msgConetxStr);
//
//        MessageHeader messageHeader = new MessageHeader();
//        messageHeader.setMsgType(msgType);
//        // 根据消息类型，修正targetIp
//        // 网关直接处理的请求，不做转发
//        if (msgType == OperationType.HEART.getOpCode() ||
//                msgType == OperationType.REGISTRY.getOpCode() ||
//                msgType == OperationType.EVENT_PACKAGE.getOpCode()) {
//            messageHeader.setSourceIp(channelIp);
//            messageHeader.setTargetIp(channelIp);
//        } else if (msgType == OperationType.CONTACT_CONTROLLER.getOpCode()) {   // 页面端的设备重启消息，需要回转给页面
//            messageHeader.setSourceIp(targetIp);
//            messageHeader.setTargetIp(channelIp);
//        } else {
//            messageHeader.setSourceIp(channelIp);
//            messageHeader.setTargetIp(targetIp);
//        }
//        this.messageHeader = messageHeader;
//
//        // 消息体解析
//        String msgBodyStr = msgConetxStr;
//        T body = decodeMsgBody(msgType, msgBodyStr);
//        this.messageBody = body;
//
//        Class<T> bodyClazz = getMessageBodyDecodeClass(opCode);
//        T body = JsonUtil.fromJson(msg.toString(StandardCharsets.UTF_8), bodyClazz);
//        this.messageBody = body;
//
//        // 控制心跳数据打印
//        if (messageHeader.getMsgType() != OperationType.HEART.getOpCode()) {
////            log.info("解析结果：{} - {}", messageHeader.toString(), messageBody.toString());
//            log.info("[decode]{} <- [{}]", messageHeader.getSourceIp(), contextStr);
//        }
        return true;
    }

    private T decodeMsgBody(int msgType, String msgBodyStr) {
        Class<T> bodyClazz = getMessageBodyDecodeClass(msgType);
        List<Field> allFields = getAllFields(bodyClazz);
        T body = null;
        try {
            body = bodyClazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            log.error("创建消息对象失败, e");
        }

        // 根据字段长度判断是否需要对协议进行具体解码
        List<String> fieldVals;
        if (allFields.size() > 1) {
            fieldVals = new ArrayList<>(Arrays.asList(msgBodyStr.split(MSG_SPLITTER)));
        } else {
            fieldVals = new ArrayList<>();
            fieldVals.add(msgBodyStr);
        }

        for (Field field : allFields) {
            CmdProp cmdProp = field.getAnnotation(CmdProp.class);
            if (cmdProp == null) {
                continue;
            }

            int idx = cmdProp.index() - 1;
            if (allFields.size() <= idx) {
                log.error("消息体参数个数小于{}，解析失败", idx);
                return null;
            } else {
                field.setAccessible(true);
                try {
                    String fieldValStr = fieldVals.get(idx);
                    String codecMethod = cmdProp.deCodec();
                    Object val = StringUtils.hasText(fieldValStr) ? null : fieldValStr;

                    // 字段解码
                    val = codec(fieldValStr, codecMethod, field.getType());

                    field.set(body, val);
                } catch (IllegalAccessException e) {
                    log.error("设置字段值失败: {}={}", field.getName(), fieldVals.get(idx));
                    return null;
                }
            }
        }

        return body;
    }



    private static List<Field> getAllFields(Class cls) {
        Field[] fields = cls.getDeclaredFields();
        List<Field> allFields = new ArrayList<>(Arrays.asList(fields));
        return allFields;
    }

    public static List<CmdPropParam> getSortedParams(Class cls) {
        List<Field> allFields = getAllFields(cls);
        List<CmdPropParam> sParams = new ArrayList<>();
        for (Field field : allFields) {
            CmdProp cmdProp = field.getAnnotation(CmdProp.class);
            if (cmdProp != null) {
                sParams.add(new CmdPropParam(cmdProp.index(), cmdProp.defaultValue(), cmdProp.enCodec(), field));
            }
        }
        Collections.sort(sParams);
        return sParams;
    }

    private Object codec(Object fieldVal, String codecMethod, Class<?> fileType) {
        Object val;

        // 字符串，无需转换
        if (Constants.ENCODER_TO_STR.equals(codecMethod)) {
            val = fieldVal.toString();
            return val;
        }

        // 根据编码方法进行转换
        try {
            Method method = FieldParser.class.getDeclaredMethod(codecMethod, fileType);
            val = method.invoke(null, fieldVal);
        } catch (NoSuchMethodException e) {
            log.error("未定义解码方法：{}，消息解析失败", codecMethod, e);
            return null;
        } catch (InvocationTargetException e) {
            log.error("调用解码方法{}发生异常，消息解析失败", codecMethod, e);
            return null;
        } catch (IllegalAccessException e) {
            log.error("设置字段值失败");
            return null;
        }

        return val;
    }
}
