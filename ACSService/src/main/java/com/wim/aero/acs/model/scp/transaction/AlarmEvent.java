package com.wim.aero.acs.model.scp.transaction;

public interface AlarmEvent {
    public int getDeviceState(int tranCode);
    public int getStateCode(int tranCode);
}
