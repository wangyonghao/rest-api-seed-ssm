package com.zg.restboot.sys.user.service.dto;

/**
 * 用户表(User)UpdateDTO
 *
 * @author wangyonghao@163.com
 * @date 2021-01-05 17:26:47
 */
public class UserUpdateDTO {
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码，MD5加密
     */
    private String password;

    /**
     * 状态，1-激活 2-停用
     */
    private Integer state;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

}
