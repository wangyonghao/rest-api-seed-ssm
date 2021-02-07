package com.zg.restboot.sys.user.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zg.restboot.common.exception.DataNotFoundException;
import com.zg.restboot.common.exception.ServerException;
import com.zg.restboot.common.page.PageParam;
import com.zg.restboot.common.page.PageResult;
import com.zg.restboot.sys.user.domain.dto.UserAddDTO;
import com.zg.restboot.sys.user.domain.dto.UserQueryDTO;
import com.zg.restboot.sys.user.domain.dto.UserUpdateDTO;
import com.zg.restboot.sys.user.domain.entity.UserEntity;
import com.zg.restboot.sys.user.domain.vo.UserVO;
import com.zg.restboot.sys.user.repository.mapper.UserMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户资源库
 *
 * @author wangyonghao@163.com
 * @version 2021.02.06
 */
@Repository
public class UserRepository {
    @Resource
    private UserMapper mapper;

    public UserVO getById(Long id) {
        return UserVO.wrapEntity(this.mapper.selectById(id));
    }

    public List<UserVO> list(UserQueryDTO queryDTO) {
        List<UserEntity> entities = this.mapper.selectList(new QueryWrapper<>(queryDTO.toEntity()));
        return entities.stream().map(UserVO::wrapEntity).collect(Collectors.toList());
    }

    public PageResult<UserVO> listPage(UserQueryDTO queryDTO, PageParam pageParam) {
        IPage<UserEntity> page = this.mapper.selectPage(pageParam.extractIPage(), new QueryWrapper<>(queryDTO.toEntity()));
        return PageResult.wrapIPage(page, UserVO::wrapEntity);
    }

    public UserVO add(UserAddDTO addDTO) {
        UserEntity entity = addDTO.toEntity();
        this.mapper.insert(entity);
        return this.getById(entity.getId());
    }

    public UserVO updateById(Long id, UserUpdateDTO updateDTO) {
        DataNotFoundException.checkNotNull(this.getById(id), "用户不存在");
        this.mapper.updateById(updateDTO.toEntity());
        return this.getById(id);
    }

    public void deleteById(Long id) {
        DataNotFoundException.checkNotNull(this.getById(id), "用户不存在");
        int effectiveCount = this.mapper.deleteById(id);
        if (effectiveCount != 1) {
            throw new ServerException("更新用户失败");
        }
    }
}
