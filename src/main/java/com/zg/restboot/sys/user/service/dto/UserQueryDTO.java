package com.zg.restboot.sys.user.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 用户表(User)QueryDTO
 *
 * @author wangyonghao@163.com
 * @since 2021-01-06 13:20:40
 */
@ApiModel("UserQueryDTO")
@Getter
@Setter
public class UserQueryDTO {
    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String username;
    /**
     * 密码，MD5加密
     */
    @ApiModelProperty("密码，MD5加密")
    private String password;
    /**
     * 状态，1-激活 2-停用
     */
    @ApiModelProperty("状态，1-激活 2-停用")
    private Integer state;
}
