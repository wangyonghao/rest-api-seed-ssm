##导入宏定义
$!define

#purlarize($tool.firstLowerCase($tableInfo.name))
##定义初始变量
#set($packageName = $tool.append($tableInfo.savePackageName, ".", $tool.firstLowerCase($tableInfo.name)))
#set($className = $tool.append($tableInfo.name, "Controller"))
#set($entityName = $!tool.append($!tool.firstLowerCase($!tableInfo.name), "Entity"))

##拿到主键
#if(!$tableInfo.pkColumn.isEmpty())
    #set($pk = $tableInfo.pkColumn.get(0))
#end

##设置回调
$!callback.setFileName($tool.append($className, ".java"))
$!callback.setSavePath($tool.append($tableInfo.savePath, "/", $tool.firstLowerCase($tableInfo.name), "/controller"))

#if($packageName)package $!{packageName}.#{end}controller;

import com.zg.restboot.common.page.PageParam;
import com.zg.restboot.common.page.PageResult;
import $!{packageName}.dao.entity.$!{tableInfo.name}Entity;
import $!{packageName}.service.$!{tableInfo.name}Service;
import $!{packageName}.service.dto.$!{tableInfo.name}InsertDTO;
import $!{packageName}.service.dto.$!{tableInfo.name}QueryDTO;
import $!{packageName}.service.dto.$!{tableInfo.name}UpdateDTO;
import $!{packageName}.service.vo.$!{tableInfo.name}VO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

##表注释（宏定义）
#tableComment("表控制层")
@Api(tags="$!{tableInfo.comment}($!{tableInfo.name})接口")
@RestController
@RequestMapping("$!{puralName}")
public class $!{className} {
    @Resource
    private $!{tableInfo.name}Service service;


    /**
     * 新增数据
     *
     * @param $!entityName 实体对象
     * @return 新增结果
     */
    @ApiOperation("新增数据")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public $!{tableInfo.name}VO save(@RequestBody $!{tableInfo.name}InsertDTO dto) {
        return this.service.save(dto);
    }

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param $!entityName 查询实体
     * @return 所有数据
     */
    @ApiOperation("分页查询所有数据")
    @GetMapping
    public PageResult<$!{tableInfo.name}VO> find(PageParam pageParam, $!{tableInfo.name}QueryDTO dto) {
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
    public $!{tableInfo.name}VO get(@PathVariable $!pk.shortType id) {
        return this.service.get(id);
    }

    /**
     * 修改单条数据
     *
     * @param $!{entityName} 实体对象
     * @return 修改结果
     */
    @ApiOperation("修改单条数据")
    @PutMapping("{id}")
    public $!{tableInfo.name}VO update(@PathVariable $!pk.shortType id, @RequestBody $!{tableInfo.name}UpdateDTO dto) {
        return this.service.update(id,dto);
    }

    /**
     * 删除单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation("删除单条数据")
    @DeleteMapping("{id}")
    public void delete(@PathVariable $!pk.shortType $!pk.name, HttpServletResponse response) {
        $!{tableInfo.name}VO vo = this.service.get($!pk.name);
        if(Objects.isNull(vo)){
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
    public void delete(@RequestParam("ids") List<Long> idList) {
        this.service.delete(idList);
    }
}