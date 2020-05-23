package com.kevin.online.common;

import com.kevin.online.common.constants.ResultCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kevin
 * 全局统一返回类
 */
@Data
@ApiModel(value = "全局统一返回结果")
public class R {

    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回信息")
    private String msg;

    @ApiModelProperty(value = "返回数据")
    private Map<String,Object> data = new HashMap<>();


    private R(){};

    /**
     * 操作成功
     * @return
     */
    public static R ok() {
        R r = new R();
        r.setSuccess(ResultCode.SUCCESS.getSuccess());
        r.setCode(ResultCode.SUCCESS.getCode());
        r.setMsg(ResultCode.SUCCESS.getMsg());
        return r;
    }

    /**
     * 操作失败
     * @return
     */
    public static R error() {
        R r = new R();
        r.setSuccess(ResultCode.UNKNOWN_REASON.getSuccess());
        r.setCode(ResultCode.UNKNOWN_REASON.getCode());
        r.setMsg(ResultCode.UNKNOWN_REASON.getMsg());
        return r;
    }


    public static R setResult(ResultCode resultCode) {
        R r = new R();
        r.setSuccess(resultCode.getSuccess());
        r.setCode(resultCode.getCode());
        r.setMsg(resultCode.getMsg());
        return r;
    }

    public R success(Boolean success) {
        this.setSuccess(success);
        return this;
    }

    public R msg(String msg) {
        this.setMsg(msg);
        return this;
    }


    public R code(Integer code) {
        this.setCode(code);
        return this;
    }

    public R data(String key,Object value) {
        this.data.put(key,value);
        return this;
    }

    public R data(Map<String,Object> map) {
        this.setData(map);
        return this;
    }
    


}
