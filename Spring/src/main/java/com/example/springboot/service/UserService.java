package com.example.springboot.service;

import cn.hutool.core.util.StrUtil;
import com.example.springboot.common.Constants;
import com.example.springboot.common.Result;
import com.example.springboot.entity.User;
import com.example.springboot.entity.UserDTO;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public List<User> findAll(){
        return userMapper.findAll();
    };

    public Result save(User user) {
        if(user.getId()==null){
            Integer insert = userMapper.insert(user);
            String s = String.valueOf(insert);
            if (s.equals("1")){
                return Result.Success();
            }else {
                return Result.Error();
            }
        }else {
            String s = String.valueOf(userMapper.update(user));
            return s.equals("1")?Result.Success():Result.Error();
        }
    }

    public Integer deleteById(Integer id) {
        return userMapper.deleteById(id);
    }

    public Map<String, Object> findPage(Integer pageNum, Integer pageSize,String username,String email,String address) {
        int Num=(pageNum-1)*pageSize;
        List<User> data=userMapper.findPage(Num,pageSize,username,email,address);
        Integer total=Integer.valueOf((int)findAll().stream().count());
        Map<String,Object> result=new HashMap();
        result.put("data",data);
        result.put("total",total);
        return result;


    }

    public Integer deleteByListId(List<Integer> ids) {
        List<Integer> result=new ArrayList<>();
        for (Integer integer : ids) {
            Integer i=userMapper.deleteById(integer);
            result.add(i);
        }
        for (Integer integer : result) {
            if (integer==0){
                return 0;
            }
        }
        return 1;
    }

    //登录
    public Result login(UserDTO userDTO){
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        if(StrUtil.isBlank(username)||StrUtil.isBlank(password)){
            return Result.Error(Constants.Code_400,"参数错误");
        }
        List<UserDTO> userDTOS = userMapper.selectByNP(username, password);
        if(userDTOS.isEmpty()){
            return Result.Error(Constants.Code_601,"账号未注册，请先注册");
        }
        if(userDTOS.size()>1){
            throw new ServiceException(Constants.Code_600,"数据库查询到多个");
        }
//        BeanUtils.copyProperties(userDTOS,userDTO1,"true");
        String token = JwtUtils.getToken(userDTOS.get(0).getId().toString(), userDTOS.get(0).getPassword().toString());
        userDTOS.get(0).setToken(token);
        return Result.Success(userDTOS);
    }


    public Result registor(UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        String s = String.valueOf(userMapper.inserts(username,password));
        return s.equals("1")?Result.Success():Result.Error();
    }

    public Result selectByUsername(String username) {
        List<User> list = userMapper.selectByUsername(username);
        return list.isEmpty()?Result.Error(Constants.Code_601,"数据库查询到0个"):Result.Success(list);
    }

    public User selectById(Integer id) {

        return  userMapper.selectById(id);
    }
}
