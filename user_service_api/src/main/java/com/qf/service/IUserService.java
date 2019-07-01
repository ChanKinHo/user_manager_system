package com.qf.service;

import com.qf.entity.User;

import java.util.List;
import java.util.Map;

public interface IUserService {

    public int addUser(User user);

    public List<User> checkUser(Map<String,Object> map);

    public int updateUser(User user);
}
