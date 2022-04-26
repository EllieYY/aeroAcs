package com.wim.aero.acs.model.scp;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @title: ScpSeq
 * @author: Ellie
 * @date: 2022/04/26 15:36
 * @description:
 **/
@Data
@AllArgsConstructor
public class ScpSeq {
    private int scpId;
    private long seq;
}
