package com.zg.restboot.user.service;

import com.zg.restboot.user.entity.User;
import com.zg.restboot.user.vo.UserQuery;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户业务类
 * @author wangyonghao
 * @date 12/15/20
 */
public interface UserService {
    User save(User user);

    List<User> find(UserQuery userQuery);

    User get(String id);

    User update(User user);

    void delete(String id);

    @Transactional(rollbackFor = Exception.class)
    void deleteAll(String[] ids);
}
