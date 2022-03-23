package com.wim.aero.acs.protocol;

import lombok.Data;

/**
 * @title: TimeSet
 * @author: Ellie
 * @date: 2022/03/14 16:13
 * @description: 时间同步。4.4 Command 302: Time Set
 **/
@Data
public class TimeSet {
    private int scpNumber;
    private long customTime; // 0 - 不使用， else - 到1970的秒数
}
