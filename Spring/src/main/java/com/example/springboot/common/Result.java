package com.example.springboot.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*定义统一返回的值的类型*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private String code;
    private String msg;
    private Object data;

    public static Result Success(){
        return new Result(Constants.Code_200,"成功",null);
    }
    public static Result Success(Object data){
        return new Result(Constants.Code_200,"成功",data);
    }
    public static Result Error(){
        return new Result(Constants.Code_500,"系统异常",null);
    }
    public static Result Error(String code,String msg){
        return new Result(code,msg,null);
    }
}
