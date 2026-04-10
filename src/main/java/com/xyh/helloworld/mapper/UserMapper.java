package com.xyh.helloworld.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xyh.helloworld.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    // 继承 BaseMapper 后已拥有基础的 CRUD 方法，无需额外编写
}