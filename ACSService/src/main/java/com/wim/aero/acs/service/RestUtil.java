package com.wim.aero.acs.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wim.aero.acs.aop.excption.ServiceException;
import com.wim.aero.acs.config.CommServiceInfo;
import com.wim.aero.acs.model.command.ScpCmd;
import com.wim.aero.acs.model.command.ScpCmdResponse;
import com.wim.aero.acs.model.result.HttpResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.zip.Deflater;

/**
 * @Description : 请求封装
 * @Author : Ellie
 */
@Slf4j
@Service
public class RestUtil {
    private final RestTemplate restTemplate;
    private final CommServiceInfo commServiceInfo;
    @Autowired
    public RestUtil(RestTemplate restTemplate, CommServiceInfo commServiceInfo) {
        this.restTemplate = restTemplate;
        this.commServiceInfo = commServiceInfo;
    }

    /**
     * 发送单条指令
     * @param cmd
     * @return
     */
    public ScpCmdResponse sendSingleCmd(ScpCmd cmd) {
        String url = commServiceInfo.getUrl() + "/SingleCmd";

        ObjectMapper mapper = new ObjectMapper();
        mapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String cmdStr = "";
        try {
            cmdStr = mapper.writeValueAsString(cmd);
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info("[sendSingleCmd]- {}", cmdStr);

        HttpResult result = post(url, cmdStr);
        if (result.getCode() > 300) {
            log.error("{} - {}", result.getCode(), result.getBody());
            throw new ServiceException("rest template error code:" + result.getCode());
        }

        try {
            ScpCmdResponse pts = mapper.readValue(result.getBody(), ScpCmdResponse.class);
            log.info(pts.toString());
            return pts;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 发送多条指令
     * @param cmdList
     * @return
     */
    public List<ScpCmdResponse> sendMultiCmd(List<ScpCmd> cmdList) {
        String url = commServiceInfo.getUrl() + "/MultiCmd";

        ObjectMapper mapper = new ObjectMapper();
        mapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String cmdStr = "";
        try {
            cmdStr = mapper.writeValueAsString(cmdList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info("[sendMultiCmd]- {}", cmdStr);

        HttpResult result = post(url, cmdStr);
        if (result.getCode() > 300) {
            log.error("{} - {}", result.getCode(), result.getBody());
            throw new ServiceException("rest template error code:" + result.getCode());
        }

        try {
            List<ScpCmdResponse> pts = mapper.readValue(result.getBody(), new TypeReference<List<ScpCmdResponse>>() {});
//            log.info(pts.toString());
            return pts;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * @Author: Ellie
     * @Description: get请求，查询字符串是在URL中发送的
     * @Params: [url]
     * @Return: java.lang.String
     */
    public HttpResult get(String url) {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        return new HttpResult(responseEntity.getStatusCodeValue(),
                responseEntity.getBody());
    }

    public HttpResult post(String url, String data) {
        log.info("[Post]{} - {}", url, data);
        byte[] content = data.getBytes();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN));
        HttpEntity<byte[]> entity = new HttpEntity<byte[]>(content, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        return new HttpResult(response.getStatusCodeValue(), response.getBody());
    }


    /** MD5加密 */
    public String CalculateMd5Hash(String content) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(content.getBytes());
            byte[] b = md.digest();

            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }

            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /** 数据压缩 */
    private byte[] compress(byte[] data) {
        byte[] output = new byte[0];

        Deflater compresser = new Deflater();

        compresser.reset();
        compresser.setInput(data);
        compresser.finish();
        ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
        try {
            byte[] buf = new byte[1024];
            while (!compresser.finished()) {
                int i = compresser.deflate(buf);
                bos.write(buf, 0, i);
            }
            output = bos.toByteArray();
        } catch (Exception e) {
            output = data;
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        compresser.end();
        return output;
    }

}