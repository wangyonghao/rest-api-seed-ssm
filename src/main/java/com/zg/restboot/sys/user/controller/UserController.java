package com.zg.restboot.sys.user.controller;

import com.zg.restboot.common.page.PageParam;
import com.zg.restboot.common.page.PageResult;
import com.zg.restboot.sys.user.domain.dto.UserAddDTO;
import com.zg.restboot.sys.user.domain.dto.UserQueryDTO;
import com.zg.restboot.sys.user.domain.dto.UserUpdateDTO;
import com.zg.restboot.sys.user.domain.vo.UserVO;
import com.zg.restboot.sys.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

/**
 * 用户 接口
 *
 * @author wangyonghao@163.com
 * @version 2021.02.06
 */
@Api(tags = "用户接口")
@RestController
public class UserController {
    @Resource
    private UserService service;

    @ApiOperation("新增一个用户")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("users")
    public UserVO add(@RequestBody UserAddDTO addDTO) {
        return this.service.add(addDTO);
    }

    @ApiOperation("查询用户")
    @GetMapping("users")
    public List<UserVO> search(UserQueryDTO queryDTO, @Nullable PageParam pageParam, HttpServletResponse response) {
        if (Objects.nonNull(pageParam)) {
            PageResult<UserVO> pageResult = this.service.searchPage(queryDTO, pageParam);
            pageResult.addToHeader(response);
            return pageResult.getData();
        } else {
            return this.service.search(queryDTO);
        }
    }

    @ApiOperation("按主键查找用户")
    @GetMapping("users/{id}")
    public UserVO get(@PathVariable Long id) {
        return this.service.getById(id);
    }

    @ApiOperation("更新指定用户")
    @PutMapping("users/{id}")
    public UserVO update(@PathVariable Long id, @RequestBody UserUpdateDTO updateDTO) {
        return this.service.updateById(id, updateDTO);
    }

    @ApiOperation("删除指定用户")
    @DeleteMapping("users/{id}")
    public void delete(@PathVariable Long id) {
        this.service.deleteById(id);
    }
}
