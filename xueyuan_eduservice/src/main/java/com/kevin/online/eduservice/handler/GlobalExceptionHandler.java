package com.kevin.online.eduservice.handler;

import com.kevin.online.common.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 *
 * @author kevin
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 对所有异常进行相同的处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R errorException(Exception e) {
        e.printStackTrace();
        return R.error().msg("出现异常");
    }


    /**
     * 对特定异常进行处理:ArithmeticException（例如：系统出现了0为除数）
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R errorException(ArithmeticException e) {
        e.printStackTrace();
        return R.error().msg("0不能为除数");
    }


    /**
     * 自定义异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(EduException.class)
    @ResponseBody
    public R errorException(EduException e) {
        e.printStackTrace();
        return R.error().msg(e.getMsg()).code(e.getCode());
    }


}
