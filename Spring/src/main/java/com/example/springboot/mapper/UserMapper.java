package com.example.springboot.mapper;

import com.example.springboot.entity.User;
import com.example.springboot.entity.UserDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from springvuebd.sys_user")
    List<User> findAll();

    Integer insert(User user);

    Integer update(User user);

    @Delete("delete from springvuebd.sys_user where id=#{id}")
    Integer deleteById(Integer id);


    List<User> findPage(Integer pageNum, Integer pageSize, String username, String email, String address);

    @Select("select *from springvuebd.sys_user where username=#{username} and password=#{password}")
    List<UserDTO> selectByNP(String username, String password);

    @Insert("insert into springvuebd.sys_user ( username, password) values(#{username},#{password})")
    Integer inserts(String username,String password);

    @Select("select * from springvuebd.sys_user where username=#{username};")
    List<User> selectByUsername(String username);

    @Select("select * from springvuebd.sys_user where id=#{id};")
    User selectById(Integer id);
}
