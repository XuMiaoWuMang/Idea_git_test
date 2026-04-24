package com.xyh.helloworld.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xyh.helloworld.common.Result;
import com.xyh.helloworld.dto.UserDTO;

public interface UserService {
    Result<String> register(UserDTO userDTO);
    Result<String> login(UserDTO userDTO);
    Result<String> getUserById(Long id);   // 新增查询接口
    Result<Object> getUserPage(Integer pageNum, Integer pageSize);
}