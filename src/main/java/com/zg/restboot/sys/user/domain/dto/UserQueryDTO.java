package com.zg.restboot.sys.user.domain.dto;

import com.zg.restboot.sys.user.domain.entity.UserEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

/**
 * 用户查询DTO
 *
 * @author wangyonghao@163.com
 * @version 2021.02.06
 */
@ApiModel
@Getter
@Setter
public class UserQueryDTO {
    /**
     * 账号
     */
    @ApiModelProperty("账号")
    private String username;
    /**
     * 昵称
     */
    @ApiModelProperty("昵称")
    private String nickname;

    /**
     * 包装实体
     */
    public UserEntity toEntity() {
        UserEntity entity = new UserEntity();
        BeanUtils.copyProperties(this, entity);
        return entity;
    }
}
