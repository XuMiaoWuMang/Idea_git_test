package com.xyh.helloworld.service;

import com.xyh.helloworld.common.Result;
import com.xyh.helloworld.dto.UserDTO;

public interface UserService {
    Result<String> register(UserDTO userDTO);
    Result<String> login(UserDTO userDTO);
}