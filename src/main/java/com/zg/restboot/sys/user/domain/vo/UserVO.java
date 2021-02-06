package com.zg.restboot.sys.user.domain.vo;

import com.zg.restboot.sys.user.domain.entity.UserEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.time.Instant;

/**
 * 用户VO
 *
 * @author wangyonghao@163.com
 * @version 2021.02.06
 */
@ApiModel("UserVO")
public class UserVO implements Serializable {
    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Long id;
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
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Instant createTime;
    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private Instant updateTime;

    /**
     * 包装实体
     */
    public static UserVO wrapEntity(UserEntity entity) {
        UserVO vo = new UserVO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }
}
