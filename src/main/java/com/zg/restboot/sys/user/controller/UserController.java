package com.zg.restboot.sys.user.controller;

import com.zg.restboot.common.page.PageParam;
import com.zg.restboot.common.page.PageResult;
import com.zg.restboot.sys.user.service.UserService;
import com.zg.restboot.sys.user.service.dto.UserInsertDTO;
import com.zg.restboot.sys.user.service.dto.UserQueryDTO;
import com.zg.restboot.sys.user.service.dto.UserUpdateDTO;
import com.zg.restboot.sys.user.service.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

/**
 * 用户表(User)表控制层
 *
 * @author wangyonghao@163.com
 * @since 2021-01-06 13:59:00
 */
@Api(tags = "用户表(User)接口")
@RestController
@RequestMapping("users")
public class UserController {
    @Resource
    private UserService service;


    /**
     * 新增数据
     *
     * @param userEntity 实体对象
     * @return 新增结果
     */
    @ApiOperation("新增数据")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UserVO save(@RequestBody UserInsertDTO dto) {
        return this.service.save(dto);
    }

    /**
     * 分页查询所有数据
     *
     * @param page       分页对象
     * @param userEntity 查询实体
     * @return 所有数据
     */
    @ApiOperation("分页查询所有数据")
    @GetMapping
    public PageResult<UserVO> find(PageParam pageParam, UserQueryDTO dto) {
        return this.service.find(pageParam, dto);
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation("通过主键查询单条数据")
    @GetMapping("{id}")
    public UserVO get(@PathVariable Long id) {
        return this.service.get(id);
    }

    /**
     * 修改单条数据
     *
     * @param userEntity 实体对象
     * @return 修改结果
     */
    @ApiOperation("修改单条数据")
    @PutMapping("{id}")
    public UserVO update(@PathVariable Long id, @RequestBody UserUpdateDTO dto) {
        return this.service.update(id, dto);
    }

    /**
     * 删除单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation("删除单条数据")
    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id, HttpServletResponse response) {
        UserVO vo = this.service.get(id);
        if (Objects.isNull(vo)) {
            response.setStatus(HttpStatus.NO_CONTENT.value());
            return;
        }
        this.service.delete(id);
    }


    /**
     * 批量删除数据
     *
     * @param ids 主键集合
     * @return 删除结果
     */
    @ApiOperation("批量删除数据")
    @DeleteMapping
    public void delete(@RequestParam("ids") List<Long> idList, HttpServletResponse response) {
        if(!this.service.delete(idList)){
            response.setStatus(HttpStatus.NO_CONTENT.value());
        }
    }
}
