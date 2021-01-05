package com.zg.restboot.sys.user.dao.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * 用户表(User)分页查询结构体
 *
 * @author wangyonghao@163.com
 * @date 2021-01-05 17:25:28
 */
@TableName("sys_user")
public class UserEntity implements Serializable {
    /**
     * 主键，雪花ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 密码，MD5加密
     */
    @TableField("password")
    private String password;

    /**
     * 状态，1-激活 2-停用
     */
    @TableField("state")
    private Integer state;

    /**
     * 删除标识，0-未删除 1-已删除
     */
    @TableField(value = "deleted", fill = FieldFill.INSERT)
    @TableLogic
    private Object deleted;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Instant createTime;

    /**
     * 创建者
     */
    @TableField(value = "create_user", fill = FieldFill.INSERT)
    private Long createUser;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @Version
    private Instant updateTime;

    /**
     * 更新者
     */
    @TableField(value = "update_user", fill = FieldFill.INSERT_UPDATE)
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

    public Object getDeleted() {
        return deleted;
    }

    public void setDeleted(Object deleted) {
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
