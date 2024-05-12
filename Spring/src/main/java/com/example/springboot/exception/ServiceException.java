package com.example.springboot.exception;

import com.example.springboot.common.Result;
import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException{
    private String code;

    public ServiceException(String code,String msg){
        super(msg);
        this.code=code;
    }
}
