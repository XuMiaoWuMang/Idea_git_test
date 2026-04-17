package com.xyh.helloworld.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xyh.helloworld.common.Result;
import com.xyh.helloworld.common.ResultCode;
import com.xyh.helloworld.dto.UserDTO;
import com.xyh.helloworld.mapper.UserMapper;
import com.xyh.helloworld.entity.User;
import com.xyh.helloworld.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result<String> register(UserDTO userDTO) {
        // 1. 校验用户名是否已存在
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, userDTO.getUsername());
        User existUser = userMapper.selectOne(wrapper);
        if (existUser != null) {
            return Result.error(ResultCode.USER_HAS_EXISTED);
        }

        // 2. 组装实体对象
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());   // 实际项目需加密

        // 3. 插入数据库
        userMapper.insert(user);
        return Result.success("注册成功");
    }

    @Override
    public Result<String> login(UserDTO userDTO) {
        // 1. 根据用户名查询
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, userDTO.getUsername());
        User dbUser = userMapper.selectOne(wrapper);

        // 2. 校验用户是否存在及密码是否正确
        if (dbUser == null) {
            return Result.error(ResultCode.USER_NOT_EXIST);
        }

        // 2. 校验密码是否正确
        if (!dbUser.getPassword().equals(userDTO.getPassword())) {
            return Result.error(ResultCode.PASSWORD_ERROR);
        }

        // 3. 登录成功，生成模拟 Token（示例：前缀 + UUID）
        String token = "Bearer " + UUID.randomUUID().toString();
        return Result.success(token);
    }

    @Override
    public Result<String> getUserById(Long id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            return Result.error(ResultCode.USER_NOT_EXIST);
        }
        // 返回用户信息（可按需转换为 DTO，此处简化返回用户名）
        return Result.success("用户名: " + user.getUsername());
    }
}