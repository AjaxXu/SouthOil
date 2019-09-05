package com.k2data.precast.mapper;

import com.k2data.precast.entity.dto.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author thinkpad
 */
public interface UserMapper {

    /**
     * 通过名称获取用户
     *
     * @param name
     * @return User
     */
    User getUserByLoginName(@Param("loginName") String name);

    /**
     * 更新用户
     *
     * @param password
     * @param userName
     */
    void updateUser(@Param("userName") String userName, @Param("password") String password);
}
