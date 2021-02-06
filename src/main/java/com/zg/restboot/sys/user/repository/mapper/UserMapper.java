package com.zg.restboot.sys.user.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zg.restboot.sys.user.domain.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户表(User) 数据库访问层
 *
 * @author wangyonghao@163.com
 * @since 2021-01-12 21:05:31
 */
@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {

}
