package com.example.springboot.controller;


import com.example.springboot.common.Result;
import com.example.springboot.entity.Role;
import com.example.springboot.service.impl.RoleServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 文若帆
 * @since 2023-12-18
 */
@Slf4j
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleServiceImpl roleService;

    @GetMapping()
    public Result findAll(){
        log.info("角色查询");
        List<Role> list = roleService.list();
        return Result.Success(list);
    }

}
