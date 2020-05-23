package com.kevin.online.common.constants;

import lombok.Getter;

import javax.annotation.Resource;

/**
 * @author kevin
 * 全局统一返回码
 */
@Getter
public enum  ResultCode {

    /**
     * 成功
     */
    SUCCESS(true,20000,"操作成功"),

    /**
     * 未知错误
     */
    UNKNOWN_REASON(false,20001,"未知错误");

    /**
     * 标识符
     */
    private Boolean success;

    /**
     * 返回码
     */
    private Integer code;

    /**
     * 返回信息
     */
    private String msg;


    private ResultCode(){};

    ResultCode(Boolean success, Integer code, String msg) {
        this.success = success;
        this.code = code;
        this.msg = msg;
    }


}
