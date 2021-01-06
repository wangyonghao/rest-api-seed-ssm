package com.zg.restboot.sys.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zg.restboot.common.page.PageResult;
import com.zg.restboot.sys.user.dao.entity.UserEntity;
import com.zg.restboot.sys.user.service.dto.UserInsertDTO;
import com.zg.restboot.sys.user.service.dto.UserQueryDTO;
import com.zg.restboot.sys.user.service.dto.UserUpdateDTO;
import com.zg.restboot.sys.user.service.vo.UserVO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * 用户表(User)组装器
 *
 * @author wangyonghao@163.com
 * @since 2021-01-05 21:04:57
 */
public class UserAssembler {

    public static UserVO toVO(UserEntity entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        UserVO vo = new UserVO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }

    public static List<UserVO> toVOList(List<UserEntity> entities) {
        if (Objects.isNull(entities)) {
            return new ArrayList<>();
        }
        return entities.stream().map(UserAssembler::toVO).collect(Collectors.toList());
    }

    public static UserEntity toEntity(UserQueryDTO dto) {
        if (Objects.isNull(dto)) {
            return null;
        }
        UserEntity entity = new UserEntity();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    public static UserEntity toEntity(UserInsertDTO dto) {
        if (Objects.isNull(dto)) {
            return null;
        }
        UserEntity entity = new UserEntity();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    public static UserEntity toEntity(Long id, UserUpdateDTO dto) {
        if (Objects.isNull(dto)) {
            return null;
        }

        UserEntity entity = new UserEntity();
        BeanUtils.copyProperties(dto, entity);
        entity.setId(id);
        return entity;
    }

    public static PageResult<UserVO> toPageResult(IPage<UserEntity> page) {
        List<UserVO> list = page.getRecords().stream().map(UserAssembler::toVO).collect(Collectors.toList());
        return new PageResult<UserVO>(page.getTotal(), list);
    }
}
