package com.example.springboot.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.Claims;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.springboot.common.Constants;
import com.example.springboot.entity.User;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

//全局拦截器
public class Jwtinterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        //判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            //当前拦截到的不是动态方法，直接放行
            return true;
        }

        //执行认证
        if(StrUtil.isBlank(token)){
            throw new ServiceException(Constants.Code_401,"无token，请重新登录");
        }
        //获取token中的userid
        String userId;
        try{
            userId=JWT.decode(token).getAudience().get(0);
        }catch (JWTDecodeException e){
            throw new ServiceException(Constants.Code_401,"token验证失败，请重新登录");
        }
        //根据id查数据库
        User user=userService.selectById(Integer.valueOf(userId));
        if(user==null){
            throw new ServiceException(Constants.Code_401,"用户不纯在，请重新登录");
        }
        //用户密码加签验证
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try{
            jwtVerifier.verify(token);//验证token
        }catch(JWTVerificationException e){
            throw new ServiceException(Constants.Code_401,"token验证失败，请重新登录");
        }
        return true;
    }
}
