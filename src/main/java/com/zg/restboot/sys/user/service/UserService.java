package com.zg.restboot.sys.user.service;

import com.zg.restboot.common.page.PageParam;
import com.zg.restboot.common.page.PageResult;
import com.zg.restboot.sys.user.domain.dto.UserAddDTO;
import com.zg.restboot.sys.user.domain.dto.UserQueryDTO;
import com.zg.restboot.sys.user.domain.dto.UserUpdateDTO;
import com.zg.restboot.sys.user.domain.vo.UserVO;
import com.zg.restboot.sys.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户 业务
 *
 * @author wangyonghao@163.com
 * @version 2021.02.06
 */
@Service
public class UserService {
    @Resource
    private UserRepository repository;

    public UserVO getById(Long id) {
        return this.repository.getById(id);
    }

    public List<UserVO> search(UserQueryDTO queryDTO) {
        return this.repository.list(queryDTO);
    }

    public PageResult<UserVO> searchPage(UserQueryDTO queryDTO, PageParam pageParam) {
        return this.repository.listPage(queryDTO, pageParam);
    }

    @Transactional(rollbackFor = Exception.class)
    public UserVO add(UserAddDTO addDTO) {
        return this.repository.add(addDTO);
    }

    @Transactional(rollbackFor = Exception.class)
    public UserVO updateById(Long id, UserUpdateDTO updateDTO) {
        return this.repository.updateById(id, updateDTO);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        return this.repository.deleteById(id);
    }
}
