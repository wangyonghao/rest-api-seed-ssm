package com.zg.restboot.sys2.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zg.restboot.common.page.PageResult;
import com.zg.restboot.sys2.user.dao.entity.UserEntity;
import com.zg.restboot.sys2.user.service.dto.UserInsertDTO;
import com.zg.restboot.sys2.user.service.dto.UserQueryDTO;
import com.zg.restboot.sys2.user.service.dto.UserUpdateDTO;
import com.zg.restboot.sys2.user.service.vo.UserVO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author edz
 * @date 2020/12/31
 */
public class UserAssembler {
    public static UserVO toVO(UserEntity entity) {
        if(Objects.isNull(entity)){
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(entity, userVO);
        return userVO;
    }

    public static UserEntity toEntity(UserQueryDTO dto){
        if(Objects.isNull(dto)){
            return null;
        }
        UserEntity entity = new UserEntity();
        BeanUtils.copyProperties(dto , entity);
        return entity;
    }

    public static UserEntity toEntity(UserInsertDTO dto) {
        if(Objects.isNull(dto)){
            return null;
        }
        UserEntity entity = new UserEntity();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }


    public static UserEntity toEntity(UserUpdateDTO dto) {
        if(Objects.isNull(dto)){
            return null;
        }
        UserEntity entity = new UserEntity();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }


    public static PageResult<UserVO> toPageResult(IPage<UserEntity> page) {
        List<UserVO> list =  page.getRecords().stream().map(UserAssembler::toVO).collect(Collectors.toList());
        return new PageResult<UserVO>(page.getTotal(),list);
    }
}
