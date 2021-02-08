package com.zg.restboot.sys.user.domain.dto;

import com.zg.restboot.sys.user.domain.entity.UserEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;

/**
 * 用户更新DTO
 *
 * @author wangyonghao@163.com
 * @version 2021.02.07
 */
@ApiModel("UserUpdateDTO")
@Getter
@Setter
public class UserUpdateDTO {
    @ApiModelProperty("账号")
    @NotBlank
    private String username;
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
