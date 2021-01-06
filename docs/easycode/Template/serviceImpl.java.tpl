##定义初始变量
#set($packageName = $tool.append($tableInfo.savePackageName, ".", $tool.firstLowerCase($tableInfo.name)))
#set($tableName = $tool.append($tableInfo.name, "ServiceImpl"))
##设置回调
$!callback.setFileName($tool.append($tableName, ".java"))
$!callback.setSavePath($tool.append($tableInfo.savePath, "/", $tool.firstLowerCase($tableInfo.name), "/service"))

##拿到主键
#if(!$tableInfo.pkColumn.isEmpty())
    #set($pk = $tableInfo.pkColumn.get(0))
#end

#if($packageName)package $!{packageName}.#{end}service;

import com.zg.restboot.common.page.PageParam;
import com.zg.restboot.common.page.PageResult;
import $!{packageName}.dao.$!{tableInfo.name}Mapper;
import $!{packageName}.dao.entity.$!{tableInfo.name}Entity;
import $!{packageName}.service.$!{tableInfo.name}Service;
import $!{packageName}.service.dto.$!{tableInfo.name}InsertDTO;
import $!{packageName}.service.dto.$!{tableInfo.name}QueryDTO;
import $!{packageName}.service.dto.$!{tableInfo.name}UpdateDTO;
import $!{packageName}.service.vo.$!{tableInfo.name}VO;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;

/**
 * $!{tableInfo.comment}($!{tableInfo.name})表业务类
 *
 * @author $!author
 * @since $!time.currTime()
 */
@Service
public class $!{tableName} implements $!{tableInfo.name}Service {
    @Resource
    private $!{tableInfo.name}Mapper mapper;

    /**
     * 通过ID查询单条数据
     *
     * @param $!pk.name 主键
     * @return 实例对象
     */
    @Override
    public $!{tableInfo.name}VO get($!pk.shortType $!pk.name) {
        $!{tableInfo.name}Entity entity =  this.mapper.selectById(id);
        return $!{tableInfo.name}Assembler.toVO(entity);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public PageResult<$!{tableInfo.name}VO>find(PageParam pageParam, $!{tableInfo.name}QueryDTO dto) {
        IPage<$!{tableInfo.name}Entity> page = this.mapper.selectPage(PageParam.toIPage(pageParam),new QueryWrapper<>(UserAssembler.toEntity(dto)));
        return $!{tableInfo.name}Assembler.toPageResult(page);
    }


    /**
     * 新增数据
     *
     * @param $!tool.firstLowerCase($!{tableInfo.name}) 实例对象
     * @return 实例对象
     */
    @Transactional
    @Override
    public $!{tableInfo.name}VO save($!{tableInfo.name}InsertDTO insertDTO) {
        $!{tableInfo.name}Entity entity = $!{tableInfo.name}Assembler.toEntity(insertDTO);
        this.mapper.insert(entity);
        return $!{tableInfo.name}Assembler.toVO(entity);
    }

    /**
     * 修改数据
     *
     * @param $!tool.firstLowerCase($!{tableInfo.name}) 实例对象
     * @return 实例对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public $!{tableInfo.name}VO update($!pk.shortType $!pk.name, $!{tableInfo.name}UpdateDTO updateDTO) {
        $!{tableInfo.name}Entity entity = $!{tableInfo.name}Assembler.toEntity($!pk.name, updateDTO);
        this.mapper.updateById(entity);
        return this.get(id);
    }

    /**
     * 通过主键删除数据
     *
     * @param $!pk.name 主键
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean delete($!pk.shortType $!pk.name) {
        return this.mapper.deleteById(id) == 1;
    }
    
    /**
     * 批量删除数据
     *
     * @param $!pk.name 主键
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean delete(List<$!pk.shortType> ids) {
        return this.mapper.deleteBatchIds(ids) == ids.size();
    }
}