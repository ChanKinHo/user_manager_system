package com.qf.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.dao.UserMapper;
import com.qf.entity.User;
import com.qf.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public int addUser(User user) {
        return userMapper.insert(user);
    }

    @Override
    public List<User> checkUser(Map<String, Object> map) {

        List<User> users = userMapper.selectByMap(map);

        return users;
    }

    @Override
    public int updateUser(User user) {

        int i = userMapper.updateById(user);
        return i;
    }
}
