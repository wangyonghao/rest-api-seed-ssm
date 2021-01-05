package com.zg.restboot.sys2.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zg.restboot.common.page.PageParam;
import com.zg.restboot.common.page.PageResult;
import com.zg.restboot.sys2.user.service.dto.UserInsertDTO;
import com.zg.restboot.sys2.user.service.dto.UserQueryDTO;
import com.zg.restboot.sys2.user.service.dto.UserUpdateDTO;
import com.zg.restboot.sys2.user.dao.mapper.UserMapper;
import com.zg.restboot.sys2.user.dao.entity.UserEntity;
import com.zg.restboot.sys2.user.service.vo.UserVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户表(User)表服务实现类
 *
 * @author wangyonghao@163.com
 * @since 2020-12-30 20:10:19
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public UserVO get(Long id) {
        UserEntity userEntity =  this.userMapper.selectById(id);
        return UserAssembler.toVO(userEntity);
    }

    /**
     * 查询多条数据
     *
     * @param pageParam 分页参数
     * @param queryDTO 查询参数
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public PageResult<UserVO> find(UserQueryDTO queryDTO, PageParam pageParam) {
        IPage<UserEntity> page = this.userMapper.selectPage(PageParam.toIPage(pageParam),new QueryWrapper<>(UserAssembler.toEntity(queryDTO)));
        return UserAssembler.toPageResult(page);
    }
    public List<UserVO> find(UserQueryDTO queryDTO) {
        List<UserEntity> list =  this.userMapper.selectList(new QueryWrapper<>(UserAssembler.toEntity(queryDTO)));
        return list.stream().map(UserAssembler::toVO).collect(Collectors.toList());
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
        entity.setState(0);
        this.userMapper.insert(entity);
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
        UserEntity entity = UserAssembler.toEntity(updateDTO);
        entity.setId(id);
        this.userMapper.updateById(entity);
        return this.get(id) ;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean delete(Long id) {
        return this.userMapper.deleteById(id) > 0;
    }

    /**
     * 批量删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public int delete(List<Long> ids) {
        return this.userMapper.deleteBatchIds(ids);
    }
}
