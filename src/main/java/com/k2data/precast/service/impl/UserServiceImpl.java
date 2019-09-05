package com.k2data.precast.service.impl;

import com.k2data.precast.entity.dto.User;
import com.k2data.precast.mapper.UserMapper;
import com.k2data.precast.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * UserServiceImpl
 *
 * @author tianhao
 * @date 2018/12/13 15:06
 **/
@Service("userService")
public class UserServiceImpl implements IUserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user.getUserName(), user.getPassword());
    }

    @Override
    public User getUserByLoginName(String name) {
        return userMapper.getUserByLoginName(name);
    }
}
