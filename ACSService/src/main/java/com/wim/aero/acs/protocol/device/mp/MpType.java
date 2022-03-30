package com.wim.aero.acs.protocol.device.mp;

import lombok.Data;

/**
 * @title: MpType
 * @author: Ellie
 * @date: 2022/03/11 16:35
 * @description: MpGroup子项，由point type 和 point number组成
 *
 * |-------+------------------+----------------------+------------------+
 * | Value | Point type       | Point number         | Example          |
 * |-------+------------------+----------------------+------------------+
 * |   1   | Monitor Point    | Monitor point number | 1, 4(MP #4)      |
 * |   2   | Forced Open      | ACR number           | 2, 1 (F/O ACR 1) |
 * |   3   | Held Open        | ACR number           | 3, 1 (H/O ACR 1) |
 * |-------+------------------+----------------------+------------------+
 **/
@Data
public class MpType {
    private int pointType;
    private int pointNum;

    @Override
    public String toString() {
        return pointType + " " + pointNum;
    }
}
