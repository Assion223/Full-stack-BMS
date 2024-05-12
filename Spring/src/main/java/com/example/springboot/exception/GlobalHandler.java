package com.example.springboot.exception;

import com.example.springboot.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
//全局的异常捕获处理器
@ControllerAdvice
public class GlobalHandler {
    @ExceptionHandler(value =ServiceException.class)
    @ResponseBody
    public Result exceptionHandler(ServiceException e){
        return Result.Error(e.getCode(), e.getMessage());
    }

}
