package com.wim.aero.acs.aop.excption;

/**
 * @Description : 服务端异常
 * @Author : Ellie
 */
public class ServiceException extends RuntimeException {
    public ServiceException(String msg) {
        super(msg);
    }
}
