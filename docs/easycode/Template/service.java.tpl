##定义初始变量
#set($packageName = $tool.append($tableInfo.savePackageName, ".", $tool.firstLowerCase($tableInfo.name)))
#set($tableName = $tool.append($tableInfo.name, "Service"))
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
import $!{packageName}.dao.entity.$!{tableInfo.name}Entity;
import $!{packageName}.service.$!{tableInfo.name}Service;
import $!{packageName}.service.dto.$!{tableInfo.name}InsertDTO;
import $!{packageName}.service.dto.$!{tableInfo.name}QueryDTO;
import $!{packageName}.service.dto.$!{tableInfo.name}UpdateDTO;
import $!{packageName}.service.vo.$!{tableInfo.name}VO;
import java.util.List;

/**
 * $!{tableInfo.comment}($!{tableInfo.name})表服务接口
 *
 * @author $!author
 * @since $!time.currTime()
 */
public interface $!{tableName} {

    /**
     * 通过ID查询单条数据
     *
     * @param $!pk.name 主键
     * @return 实例对象
     */
    $!{tableInfo.name}VO get($!pk.shortType $!pk.name);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    PageResult<$!{tableInfo.name}VO> find(PageParam pageParam, $!{tableInfo.name}QueryDTO queryDTO);

    /**
     * 新增
     *
     * @param insertDTO 待插入的对象
     * @return 新增的对象
     */
    $!{tableInfo.name}VO save($!{tableInfo.name}InsertDTO insertDTO);

    /**
     * 修改数据
     *
     * @param updateDTO 待更新的对象
     * @return 实例对象
     */
    $!{tableInfo.name}VO update($!pk.shortType $!pk.name, $!{tableInfo.name}UpdateDTO updateDTO);

    /**
     * 通过主键删除数据
     *
     * @param $!pk.name 主键
     * @return 是否成功
     */
    boolean delete($!pk.shortType $!pk.name);

    /**
     * 批量删除数据
     *
     * @param ids 主键集合
     * @return 是否成功
     */
    boolean delete(List<$!pk.shortType> ids);
}