package com.zg.restboot.sys.user.service.dto;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户表(User)InsertDTO
 *
 * @author wangyonghao@163.com
 * @since 2021-01-06 13:20:37
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
