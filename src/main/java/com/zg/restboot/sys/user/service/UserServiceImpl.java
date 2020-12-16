package com.zg.restboot.sys.user.service;

import com.zg.restboot.sys.user.dao.UserDao;
import com.zg.restboot.sys.user.entity.User;
import com.zg.restboot.sys.user.vo.UserQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * UserService
 * @author wangyonghao
 * @date 12/15/20
 */
@Service
public class UserServiceImpl implements UserService{
    final UserDao dao;

    public UserServiceImpl(UserDao userDao) {
        this.dao = userDao;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public User save(User user) {
        dao.insert(user);
        return user;
    }

    /**
     * 查询用户
     * @param userQuery
     * @return 查询结果为空时，返回空集合
     */
    @Override
    public List<User> find(UserQuery userQuery) {
        return dao.select(userQuery);
    }

    @Override
    public User get(String id) {
        User user = dao.getById(id);
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public User update(User user) {
        dao.update(user);
        return user;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String id) {
        User user = this.get(id);
        dao.delete(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteAll(String[] ids) {
        for(String id : ids){
            this.delete(id);
        }
    }
}
