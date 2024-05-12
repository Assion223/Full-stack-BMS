package com.example.springboot.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.entity.Files;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface FilesMapper {
    @Insert("insert into springvuebd.sys_file (name, type, size, url, md5) values (#{name},#{type},#{size},#{url},#{md5})")
    void insert(Files saveFile);

    @Select("select * from springvuebd.sys_file where md5=#{md5};")
    List<Files> selectListByMd5(String md5);

    @Select("select * from springvuebd.sys_file where id=#{id};")
    Files selectById(Integer id);

    @Update("update springvuebd.sys_file set name=#{name},type=#{type},size=#{size},url=#{url},is_delete=#{isDelete},enable=#{enable},md5=#{md5} where id=#{id}")
    void updateById(Files files);


    List<Files> selectPage(Integer pageNum, Integer pageSize, String name);
}
