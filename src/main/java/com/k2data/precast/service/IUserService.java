package com.k2data.precast.service;

import com.k2data.precast.entity.dto.User;

/**
 * @author thinkpad
 */
public interface IUserService {

    /**
     * 修改用户
     *
     * @param user
     */
    void updateUser(User user);

    /**
     * 查询用户
     *
     * @param name
     * @return
     */
    User getUserByLoginName(String name);


}
