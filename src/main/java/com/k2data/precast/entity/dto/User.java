package com.k2data.precast.entity.dto;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * User
 *
 * @author tianhao
 * @date 2018/12/13 14:51
 **/
public class User {

    /**
     * 用户名
     */
    private String userName;

    /**
     * 登陆名
     */
    private String loginName;
    /**
     * 用户密码
     */
    private transient String password;
    /**
     * 用户ID
     */
    private Long userId;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @JSONField(serialize = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String passWord) {
        this.password = passWord;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
}
