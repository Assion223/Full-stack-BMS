package com.example.springboot.service.impl;


import com.example.springboot.entity.Role;
import com.example.springboot.mapper.RoleMapper;
import com.example.springboot.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 文若帆
 * @since 2023-12-18
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {


}
