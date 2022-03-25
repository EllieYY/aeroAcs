package com.wim.aero.acs.aop.excption;

/**
 * @Description : 数据库操作异常
 * @Author : Ellie
 */
public class DataDaoException extends RuntimeException {
    public DataDaoException(String msg) {
        super(msg);
    }
}
