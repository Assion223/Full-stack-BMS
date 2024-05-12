package com.example.springboot.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.Claims;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.springboot.common.Constants;
import com.example.springboot.entity.User;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.service.UserService;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.xpath.operations.Bool;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;

@Component
public class JwtUtils {
    private static UserService staticUserService;

    @Resource
    private UserService userService;
    @PostConstruct
    public void setUserService(){
        staticUserService=userService;
    }

    public static String getToken(String userId,String sign){
        return JWT.create().withAudience(userId) //userid作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(),2)) //2小时候过期
                .sign(Algorithm.HMAC256(sign));//password作为密钥
    }


    public static Boolean  parseJWT(String jwt, String sign){
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(sign)).build();
        try{
            jwtVerifier.verify(jwt);//验证token
        }catch(JWTVerificationException e){
            throw new ServiceException(Constants.Code_401,"token验证失败，请重新登录");
        }
        return true;
    }

}
