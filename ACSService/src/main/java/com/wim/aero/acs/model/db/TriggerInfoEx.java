package com.wim.aero.acs.model.db;

import com.wim.aero.acs.db.entity.TriggerInfo;
import lombok.Data;

import java.util.List;

/**
 * @title: TriggerInfoEx
 * @author: Ellie
 * @date: 2022/05/23 14:34
 * @description:
 **/
@Data
public class TriggerInfoEx extends TriggerInfo {
    private List<Integer> codeList;
}
