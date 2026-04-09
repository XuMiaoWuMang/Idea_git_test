package com.xyh.helloworld.controller;

import com.xyh.helloworld.common.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    // 1. 查询用户信息（GET /api/users/{id}）
    @GetMapping("/{id}")
    public Result<String> getUser(@PathVariable Long id) {
        String data = "查询成功，正在返回 ID 为 " + id + " 的用户信息";
        return Result.success(data);
    }

    // 2. 创建用户（注册） POST /api/users
    @PostMapping
    public Result<String> createUser(@RequestBody(required = false) Object userInfo) {
        // 模拟注册逻辑
        return Result.success("注册成功，用户已创建");
    }

    // 3. 登录接口（全局放行，无需 Token）
    @PostMapping("/login")
    public Result<String> login(@RequestBody(required = false) Object loginInfo) {
        // 实际项目中应校验用户名密码，并生成 JWT 返回
        String mockToken = "mock-jwt-token-xyz";
        return Result.success("登录成功，请使用 Authorization header 携带: " + mockToken);
    }

    // 4. 删除用户（敏感操作，需要 Token）
    @DeleteMapping("/{id}")
    public Result<String> deleteUser(@PathVariable Long id, HttpServletRequest request) {
        // 拦截器已保证 Token 存在，这里直接执行业务
        String token = request.getHeader("Authorization");
        return Result.success("用户 ID " + id + " 已删除，使用的 Token: " + token);
    }

    // 5. 更新用户（敏感操作，需要 Token）
    @PutMapping("/{id}")
    public Result<String> updateUser(@PathVariable Long id, @RequestBody(required = false) Object userInfo,
                                     HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return Result.success("用户 ID " + id + " 已更新，使用的 Token: " + token);
    }
}