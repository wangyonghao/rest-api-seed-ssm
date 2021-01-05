package com.zg.restboot.sys2.user.service.dto;

/**
 * @author edz
 * @date 2020/12/31
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
}
