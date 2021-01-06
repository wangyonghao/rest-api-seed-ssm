package com.zg.restboot.sys.user.service.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.Instant;

/**
 * 用户表(User)VO
 *
 * @author wangyonghao@163.com
 * @since 2021-01-05 20:48:18
 */
@ApiModel("sys_user")
public class UserVO implements Serializable {
    /**
     * 主键，雪花ID
     */
    @ApiModelProperty("主键，雪花ID")
    private Long id;

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

    /**
     * 删除标识，0-未删除 1-已删除
     */
    @ApiModelProperty("删除标识，0-未删除 1-已删除")
    private Boolean deleted;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Instant createTime;

    /**
     * 创建者
     */
    @ApiModelProperty("创建者")
    private Long createUser;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private Instant updateTime;

    /**
     * 更新者
     */
    @ApiModelProperty("更新者")
    private Long updateUser;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

}
