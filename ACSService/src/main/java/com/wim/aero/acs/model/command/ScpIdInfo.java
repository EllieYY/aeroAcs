package com.wim.aero.acs.model.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @title: ScpIdInfo
 * @author: Ellie
 * @date: 2022/08/03 13:22
 * @description:
 **/
@Data
public class ScpIdInfo {
    @JsonProperty("scpId")
    private String scpId;

    @JsonProperty(defaultValue = "")
    private String reason = "";

    public ScpIdInfo(int scpId) {
        this.scpId = String.valueOf(scpId);
    }
}
