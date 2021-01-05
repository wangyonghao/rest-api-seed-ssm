package com.zg.restboot.sys.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zg.restboot.common.page.PageParam;
import com.zg.restboot.common.page.PageResult;
import com.zg.restboot.sys.user.dao.UserMapper;
import com.zg.restboot.sys.user.dao.entity.UserEntity;
import com.zg.restboot.sys.user.service.dto.UserInsertDTO;
import com.zg.restboot.sys.user.service.dto.UserQueryDTO;
import com.zg.restboot.sys.user.service.dto.UserUpdateDTO;
import com.zg.restboot.sys.user.service.vo.UserVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户表(User)表业务类
 *
 * @author wangyonghao@163.com
 * @since 2021-01-05 20:14:06
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper mapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public UserVO get(Long id) {
        UserEntity entity = this.mapper.selectById(id);
        return UserAssembler.toVO(entity);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public PageResult<UserVO> find(PageParam pageParam, UserQueryDTO dto) {
        IPage<UserEntity> page = this.mapper.selectPage(PageParam.toIPage(pageParam), new QueryWrapper<>(UserAssembler.toEntity(dto)));
        return UserAssembler.toPageResult(page);
    }


    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public UserVO save(UserInsertDTO insertDTO) {
        UserEntity entity = UserAssembler.toEntity(insertDTO);
        this.mapper.insert(entity);
        return UserAssembler.toVO(entity);
    }

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public UserVO update(Long id, UserUpdateDTO updateDTO) {
        UserEntity entity = UserAssembler.toEntity(id, updateDTO);
        this.mapper.updateById(entity);
        return this.get(id);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean delete(Long id) {
        return this.mapper.deleteById(id) == 1;
    }

    /**
     * 批量删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean delete(List<Long> ids) {
        return this.mapper.deleteBatchIds(ids) == ids.size();
    }
}
