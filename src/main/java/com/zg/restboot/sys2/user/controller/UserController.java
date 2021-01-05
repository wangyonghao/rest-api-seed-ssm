package com.zg.restboot.sys2.user.controller;

import com.zg.restboot.common.page.PageParam;
import com.zg.restboot.common.page.PageResult;
import com.zg.restboot.sys2.user.service.dto.UserInsertDTO;
import com.zg.restboot.sys2.user.service.dto.UserQueryDTO;
import com.zg.restboot.sys2.user.service.dto.UserUpdateDTO;
import com.zg.restboot.sys2.user.service.vo.UserVO;
import com.zg.restboot.sys2.user.service.UserService;
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
 * @since 2020-12-30 20:54:08
 */
@Api(tags = "用户表(User)接口")
@RestController
@RequestMapping("users")
public class UserController {
    @Resource
    private UserService userService;


    /**
     * 新增数据
     *
     * @param userEntity 实体对象
     * @return 新增结果
     */
    @ApiOperation("新增数据")
    @PostMapping
    public UserVO save(@RequestBody UserInsertDTO dto) {
        return this.userService.save(dto);
    }

    /**
     * 分页查询所有数据
     *
     * @param pageParam    分页对象
     * @param userEntity 查询实体
     * @return 所有数据
     */
    @ApiOperation("分页查询所有数据")
    @GetMapping
    public PageResult<UserVO> find(UserQueryDTO queryDTO, PageParam pageParam) {
        return this.userService.find(queryDTO, pageParam);
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
        return this.userService.get(id);
    }

    /**
     * 修改单条数据
     *
     * @param userEntity 实体对象
     * @return 修改结果
     */
    @ApiOperation("修改单条数据")
    @PutMapping("{id}")
    public UserVO update(@PathVariable Long id, @RequestBody UserUpdateDTO updateDTO) {
        return this.userService.update(id, updateDTO);
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
        UserVO vo = this.userService.get(id);
        if (Objects.isNull(vo)) {
            response.setStatus(HttpStatus.NO_CONTENT.value());
            return;
        }
        this.userService.delete(id);
    }


    /**
     * 批量删除数据
     *
     * @param ids 主键集合
     * @return 删除结果
     */
    @ApiOperation("批量删除数据")
    @DeleteMapping
    public void deleteMany(@RequestParam("ids") List<Long> idList) {
        this.userService.delete(idList);
    }
}
