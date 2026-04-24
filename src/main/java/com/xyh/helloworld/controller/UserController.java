package com.xyh.helloworld.controller;

import com.xyh.helloworld.common.Result;
import com.xyh.helloworld.dto.UserDTO;
import com.xyh.helloworld.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // 注册接口 POST /api/users
    @PostMapping
    public Result<String> register(@RequestBody UserDTO userDTO) {
        return userService.register(userDTO);
    }

    // 登录接口 POST /api/users/login
    @PostMapping("/login")
    public Result<String> login(@RequestBody UserDTO userDTO) {
        return userService.login(userDTO);
    }

    // 查询用户信息（用于测试拦截器放行）
    @GetMapping("/{id}")
    public Result<String> getUser(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/page")
    public Result<Object> getUserPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "5") Integer pageSize) {
        return userService.getUserPage(pageNum, pageSize);
    }
}