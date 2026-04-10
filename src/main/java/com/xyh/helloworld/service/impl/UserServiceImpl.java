package com.xyh.helloworld.service.impl;

import com.xyh.helloworld.common.Result;
import com.xyh.helloworld.common.ResultCode;
import com.xyh.helloworld.dto.UserDTO;
import com.xyh.helloworld.service.UserService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    // 模拟数据库表，key 为 username，value 为 password
    private final Map<String, String> userDb = new HashMap<>();

    @Override
    public Result<String> register(UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();

        // 1. 校验用户名是否已存在
        if (userDb.containsKey(username)) {
            return Result.error(ResultCode.USER_HAS_EXISTED);
        }

        // 2. 存入“数据库”
        userDb.put(username, password);

        // 3. 注册成功，返回成功信息（无 Token）
        return Result.success("注册成功");
    }

    @Override
    public Result<String> login(UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();

        // 1. 校验用户是否存在
        if (!userDb.containsKey(username)) {
            return Result.error(ResultCode.USER_NOT_EXIST);
        }

        // 2. 校验密码是否正确
        String dbPassword = userDb.get(username);
        if (!dbPassword.equals(password)) {
            return Result.error(ResultCode.PASSWORD_ERROR);
        }

        // 3. 登录成功，生成模拟 Token（示例：前缀 + UUID）
        String token = "Bearer " + UUID.randomUUID().toString();
        return Result.success(token);
    }
}