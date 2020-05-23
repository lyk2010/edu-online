package com.kevin.online.eduservice.handler;

import com.kevin.online.common.constants.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kevin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EduException extends RuntimeException {

    /**
     * 异常码
     */
    private Integer code;

    /**
     * 异常信息
     */
    private String msg;



//    EduException(ResultCode resultCode) {
//        this.code = resultCode.getCode();
//        this.msg = resultCode.getMsg();
//    }



}
