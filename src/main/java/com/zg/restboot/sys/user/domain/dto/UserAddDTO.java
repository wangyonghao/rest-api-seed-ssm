package com.zg.restboot.sys.user.domain.dto;

import com.sun.istack.internal.NotNull;
import com.zg.restboot.sys.user.domain.entity.UserEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 用户新增DTO
 *
 * @author wangyonghao@163.com
 * @version 2021.02.07
 */
@ApiModel
@Getter
@Setter
public class UserAddDTO {
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("账号")
    @NotBlank
    private String username;

    @ApiModelProperty("昵称")
    @Size(max=20)
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
