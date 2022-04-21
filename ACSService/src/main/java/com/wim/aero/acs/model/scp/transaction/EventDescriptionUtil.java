package com.wim.aero.acs.model.scp.transaction;

/**
 * @title: EventDescriptionUtil
 * @author: Ellie
 * @date: 2022/04/20 18:32
 * @description:
 **/
public class EventDescriptionUtil {

    /** */
    public static String getTypeCosStatusDes(int status) {
        String des = "未知状态";
        switch (status) {
        case 0x00:
            des = "inactive";
            break;
        case 0x01:
            des = "active";
            break;
        case 0x02:
            des = "ground fault";
            break;
        case 0x03:
            des = "short";
            break;
        case 0x04:
            des = "open circuit";
            break;
        case 0x05:
            des = "foreign voltage";
            break;
        case 0x06:
            des = "non-settling error";
            break;
        case 0x07:
            des = "supervisory fault codes";
            break;
        case 0x08:
            des = "Offline";
            break;
        case 0x10:
            des = "Mask flag: set if the monitor point is MASKed";
            break;
        case 0x20:
            des = "Local mask flag: entry or exit delay in progress";
            break;
        case 0x40:
            des = "Entry delay in progress";
            break;
        case 0x80:
            des = "Not attached ";
            break;
        default:
            break;
        }
        return des;
    }

    public static String getTypeCosTranCodeDes(int tranCode) {
        String des = "未知事件";
        switch (tranCode) {
            case 0x01:
                des = "Disconnected (from an input point ID)";
                break;
            case 0x02:
                des = "Unknown (offline): no report from the ID";
                break;
            case 0x03:
                des = "Secure (or deactivate relay)";
                break;
            case 0x04:
                des = "Alarm (or activated relay: perm or temp)";
                break;
            case 0x05:
                des = "Fault";
                break;
            case 0x06:
                des = "Exit delay in progress";
                break;
            case 0x07:
                des = "Entry delay in progress";
                break;
            default:
                break;
        }
        return des;
    }
}
