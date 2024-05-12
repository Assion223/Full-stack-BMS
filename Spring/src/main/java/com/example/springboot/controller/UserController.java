package com.example.springboot.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.jwt.Claims;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.springboot.common.Result;
import com.example.springboot.entity.User;
import com.example.springboot.entity.UserDTO;
import com.example.springboot.service.UserService;
import com.example.springboot.utils.JwtUtils;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.server.PathParam;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping()
    //查询全部
    public List<User> findAll() {
        log.info("查询全部连接");
        return userService.findAll();
    }

    //添加、更新
    @PostMapping()
    public Result save(@RequestBody User user) {
        log.info("添加、更新连接");
        return userService.save(user);
    }

    //删除
    @DeleteMapping("/{id}")
    public Integer delete(@PathVariable Integer id) {
        log.info("单个删除连接");
        return userService.deleteById(id);
    }

    @PostMapping("/del/batch")
    public Integer deleteBatch(@RequestBody List<Integer> ids) {
        log.info("批量删除连接");
        return userService.deleteByListId(ids);
    }

    //分页查询
    @GetMapping("/page")
    public Map<String, Object> findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                                        @RequestParam String username, @RequestParam String email,
                                        @RequestParam String address) {
        log.info("分页查询连接");
        return userService.findPage(pageNum, pageSize, username, email, address);
    }

    //导出接口
    @SneakyThrows
    @GetMapping("/export")
    public void export(HttpServletResponse response) {
        log.info("导出连接");
        List<User> list = userService.findAll();
        //写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);//一次性写出list内的对象到excel，强制输出标题


        //浏览器响应格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        writer.close();
        IoUtil.close(out);
    }

    //导入接口
    @PostMapping("/import")
    public Boolean imp(MultipartFile file) throws IOException {
        log.info("导入连接");
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<User> list = reader.readAll(User.class);
        for (User user : list) {
            userService.save(user);
        }

        return true;
    }

    //登录接口
    @PostMapping("/login")
    public Result login(@RequestBody UserDTO userDTO){
        log.info("登录连接");
        return userService.login(userDTO);
    }
    //注册接口
    @PostMapping("/registor")
    public Result registor(@RequestBody UserDTO userDTO){
        log.info("注册请求");
        return userService.registor(userDTO);
    }
    //根据username返回数据
    @GetMapping("/{username}")
    public Result selsectByUsername(@PathVariable String username) {
        log.info("根据username返回数据连接");
        return userService.selectByUsername(username);
    }

    //请求token验证
    @GetMapping("/token")
    public Result selectToken(HttpServletRequest httpServletRequest) {
        log.info("验证token");
        String password=httpServletRequest.getHeader("password");
        String token=httpServletRequest.getHeader("token");
        Boolean parseJWT = JwtUtils.parseJWT(token, password);
        return parseJWT?Result.Success():Result.Error();
    }
}
