package com.zg.restboot.user.controller;

import com.zg.restboot.user.entity.User;
import com.zg.restboot.user.service.UserService;
import com.zg.restboot.user.vo.UserQuery;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 用户 API
 * @author wangyonghao
 * @date 12/15/20
 */
@RequestMapping("/users")
@RestController
public class UserController {
    final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    /**
     * 创建新用户
     * @param user
     * @return 返回保存后用户信息
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User add(@RequestBody User user){
        return service.save(user);
    }

    /**
     * 查询用户
     * @param userQuery
     * @return
     */
    @GetMapping
    public List<User> find(UserQuery userQuery){
        return service.find(userQuery);
    }

    /**
     * 批量更新用户
     * @param users
     */
    @PutMapping
    public void updateAll(List<User> users){
        // do somethiing
    }

    /**
     * 批量删除用户
     * @param ids 用户 id 数组
     */
    @DeleteMapping
    public void deleteAll(@NotBlank String[] ids){
        service.deleteAll(ids);
    }

    /**
     * 查询指定用户
     * @param id 指定用户id
     * @return 用户信息
     */
    @GetMapping("/{id}")
    public User get(@PathVariable String id){
        return service.get(id);
    }

    /**
     * 更新指定用户
     * @param id 指定用户id
     * @param user 待更新的用户信息
     * @return 更新后的用户信息
     */
    @PutMapping("/{id}")
    public User update(@PathVariable String id, User user){
        user.setId(id);
        return service.update(user);
    }

    /**
     * 删除指定用户
     * @param id 指定用户id
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){
        service.delete(id);
    }
}
