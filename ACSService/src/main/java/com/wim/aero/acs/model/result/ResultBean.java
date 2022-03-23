package com.wim.aero.acs.model.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description : 返回对象实体
 * @Author : Ellie
 * @Date : 2019/2/25
 */
@Data
@NoArgsConstructor
public class ResultBean<T> {
    @JsonProperty("iret")
    private int code;

    @JsonProperty("msg")
    private String msg;

    @JsonProperty("data")
    private T data;

    public ResultBean(RespCode respCode) {
        this.code = respCode.getCode();
        this.msg = respCode.getMsg();
    }

    public ResultBean(RespCode respCode, T data) {
        this.code = respCode.getCode();
        this.msg = respCode.getMsg();
        this.data = data;
    }

    public ResultBean(RespCode respCode, String msg) {
        this.code = respCode.getCode();
        this.msg = respCode.getMsg() + ": " + msg;
        this.data = data;
    }

    public ResultBean(Throwable e) {
        this.code = RespCode.FAIL.getCode();
        this.msg = e.toString();
    }
}
