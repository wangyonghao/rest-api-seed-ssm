package com.zg.restboot.sys2.user.service.dto;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author edz
 * @date 2020/12/31
 */
@ApiModel("UserInsertDTO")
@Getter
@Setter
@Builder
public class UserInsertDTO {
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
