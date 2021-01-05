package com.zg.restboot.sys2.user.service;

import com.zg.restboot.common.page.PageParam;
import com.zg.restboot.common.page.PageResult;
import com.zg.restboot.sys2.user.service.dto.UserInsertDTO;
import com.zg.restboot.sys2.user.service.dto.UserQueryDTO;
import com.zg.restboot.sys2.user.service.dto.UserUpdateDTO;
import com.zg.restboot.sys2.user.service.vo.UserVO;

import java.util.List;

/**
 * 用户表(User)表服务接口
 *
 * @author wangyonghao@163.com
 * @since 2020-12-30 20:08:23
 */
public interface UserService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    UserVO get(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    PageResult<UserVO> find(UserQueryDTO queryDTO, PageParam pageParam);

    /**
     * 新增数据
     *
     * @param userEntity 实例对象
     * @return 实例对象
     */
    UserVO save(UserInsertDTO insertDTO);

    /**
     * 修改数据
     *
     * @param userEntity 实例对象
     * @return 实例对象
     */
    UserVO update(Long id, UserUpdateDTO updateDTO);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean delete(Long id);

    /**
     * 批量删除数据
     *
     * @param ids 主键集合
     * @return 是否成功
     */
    int delete(List<Long> ids);

}
