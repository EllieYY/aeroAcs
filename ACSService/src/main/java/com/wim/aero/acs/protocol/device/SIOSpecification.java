package com.wim.aero.acs.protocol.device;

import com.wim.aero.acs.db.entity.DevXDetail;
import com.wim.aero.acs.message.Operation;
import com.wim.aero.acs.util.ProtocolFiledUtil.CmdProp;
import lombok.Data;

/** 参数配置说明
 * +--------+-------+---------+----------+----------+
 * | device | model | nInputs | nOutputs | nReaders |
 * | V100   | 190   | 7       | 4        | 4        |
 * | V200   | 191   | 19      | 2        | 0        |
 * | V300   | 192   | 5       | 12       | 0        |
 * +------------------------------------------------+
 * | X100   | 193   | 7       | 4        | 4        |
 * | X200   | 194   | 19      | 2        | 0        |
 * | X300   | 195   | 5       | 12       | 0        |
 * | X1100  | 196   | 7       | 4        | 4        |
 * +------------------------------------------------+
 * | Aperio | 57    | 193     | 64       | 64       |
 * +--------+-------+---------+----------+----------+
 *
 * 【关键参数】
 * sioNumber - SCP内不重复
 * port - 连到SCP上的端口，1 or 2
 * address - SIO地址，SCP内不重复，接两路：0~15；接一路：0~31
 *
 * flags - 调整同时发生的事件上报顺序，
 *  0x00:先低后高（物理位置）
 *  0x20:先高后低
 */

/**
 * @title: SIOCapacity
 * @author: Ellie
 * @date: 2022/03/11 08:18
 * @description: 6.3 Command 109: SIO Panel Configuration
 **/
@Data
public class SIOSpecification extends Operation {

    @CmdProp(index = 2)
    private Integer lastModified = 0;

    @CmdProp(index = 3)
    private Integer scpNumber;    // SCP number

    @CmdProp(index = 4)
    private Integer sioNumber;   // 0 ~ (nSio-1) | command 1107

    @CmdProp(index = 5)
    private Integer nInputs;

    @CmdProp(index = 6)
    private Integer nOutputs;

    @CmdProp(index = 7)
    private Integer nReaders;

    @CmdProp(index = 8)
    private Integer model;

    @CmdProp(index = 9, defaultValue = "0")
    private Integer revision = 0;

    @CmdProp(index = 10, defaultValue = "0")
    private Integer serNumLow = 0;

    @CmdProp(index = 11, defaultValue = "-1")
    private Integer serNumHigh = -1;

    @CmdProp(index = 12, defaultValue = "1")
    private Integer enable = 1;

    @CmdProp(index = 13)
    private Integer port; // same as msp1Number in SIODriver

    @CmdProp(index = 14, defaultValue = "0")
    private Integer channelOut = 0;

    @CmdProp(index = 15, defaultValue = "0")
    private Integer channelIn = 0;

    @CmdProp(index = 16)
    private Integer address;

    /** 离线周期判断 */
    @CmdProp(index = 17, defaultValue = "4")
    private Integer e_max = 4;

    @CmdProp(index = 18, defaultValue = "0")
    private Integer flags = 0;

    /** 梯控设备参数 -------------------*/
    @CmdProp(index = 19, defaultValue = "-1")
    private Integer nSioNextIn = -1;
    //    SIO number for continuation of inputs. Use -1 for not used. The continuation point is the first
    //    point of that type on the continuation card. A value of zero specifies to use the next SIO number
    //+1 as the value of nSioNextIn.
    //            Therefore, SIO 0 cannot be used as the next SIO. For maintainability, it is recommended that
    //this number be explicitly specified.

    @CmdProp(index = 20, defaultValue = "-1")
    private Integer nSioNextOut = -1;
    //SIO number for continuation of outputs. Use -1 for not used. The continuation point is the first
    //    point of that type on the continuation card. A value of zero specifies to use the next SIO number
    //+1 as the value of nSioNextOut.
    //            Therefore, SIO 0 cannot be used as the next SIO. For maintainability, it is recommended that
    //this number be explicitly specified.

    @CmdProp(index = 21, defaultValue = "-1")
    private Integer nSioNextRdr = -1;
    //SIO number for continuation of readers. Use -1 for not used. The continuation point is the first
    //    point of that type on the continuation card. A value of zero specifies to use the next SIO number
    //+1 as the value of nSioNextRdr. Therefore, SIO 0 cannot be used as the next SIO. For
    //    maintainability, it is recommended that this number be explicitly specified.

    @CmdProp(index = 22, defaultValue = "0")
    private Integer nSIOConnectTest = 0;

    @CmdProp(index = 23, defaultValue = "0")
    private Integer nSioOemCode = 0;

    @CmdProp(index = 24, defaultValue = "0")
    private Integer nSioOemMask = 0;

    public static SIOSpecification fromDb(DevXDetail detail) {
        SIOSpecification result = new SIOSpecification();
        result.setScpNumber(detail.getControllerId());
        result.setSioNumber(detail.getSioNumber());
        result.setPort(detail.getControllerPort());
        result.setAddress(detail.getAddress());

        Integer model = detail.getModel();
        result.setModel(model);
        Integer nReaders = 0;
        Integer nInputs = 0;
        Integer nOutputs = 0;

        switch (model) {
            case 190:  // v100
                nInputs = 7;
                nOutputs = 4;
                nReaders = 2;
                break;
            case 196:  // x1100
            case 193:  // x100
                nInputs = 7;
                nOutputs = 4;
                nReaders = 4;
                break;
            case 194:   // x200
            case 191:  // v200
                nInputs = 19;
                nOutputs = 2;
                break;
            case 195:   // x300
            case 192:
                nInputs = 5;
                nOutputs = 12;
                break;
            default:
                break;
        }
        result.setNInputs(nInputs);
        result.setNOutputs(nOutputs);
        result.setNReaders(nReaders);

        return result;
    }


}
