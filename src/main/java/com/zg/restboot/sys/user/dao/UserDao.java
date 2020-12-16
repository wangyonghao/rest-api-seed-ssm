package com.zg.restboot.sys.user.dao;

import com.zg.restboot.sys.user.entity.User;
import com.zg.restboot.sys.user.vo.UserQuery;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * UserDao
 * @author wangyonghao
 * @date 12/15/20
 */
@Component
public class UserDao {
    public void insert(User user) {
        // do insert
    }

    public User getById(String id) {
        // do getById
        return null;
    }

    public List<User> select(UserQuery userQuery) {
        return null;
    }

    public void update(User user) {
        // do update
    }

    public void delete(String id) {
        // do delete
    }
}
