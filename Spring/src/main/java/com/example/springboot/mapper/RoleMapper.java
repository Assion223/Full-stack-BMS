package com.example.springboot.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.example.springboot.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 文若帆
 * @since 2023-12-18
 */
public interface RoleMapper extends BaseMapper<Role> {
    @Override
    List<Role> selectList(Wrapper<Role> queryWrapper);
}
