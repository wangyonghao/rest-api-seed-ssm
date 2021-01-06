##引入宏定义
$!define
##定义初始变量
#set($packageName = $tool.append($tableInfo.savePackageName,".",$tool.firstLowerCase($tableInfo.name)))
#set($className = $tool.append($tableInfo.name, "Assembler"))
##设置回调
$!callback.setFileName($tool.append($className, ".java"))
$!callback.setSavePath($tool.append($tableInfo.savePath, "/", $tool.firstLowerCase($tableInfo.name),"/service"))

##拿到主键
#if(!$tableInfo.pkColumn.isEmpty())
    #set($pk = $tableInfo.pkColumn.get(0))
#end

## package
package $!{packageName}.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zg.restboot.common.page.PageResult;
import $!{packageName}.dao.entity.$!{tableInfo.name}Entity;
import $!{packageName}.service.dto.$!{tableInfo.name}InsertDTO;
import $!{packageName}.service.dto.$!{tableInfo.name}QueryDTO;
import $!{packageName}.service.dto.$!{tableInfo.name}UpdateDTO;
import $!{packageName}.service.vo.$!{tableInfo.name}VO;
$!autoImport
import org.springframework.beans.BeanUtils;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


##使用宏定义实现类注释信息
#tableComment("组装器")
public class $!{className} {

    public static $!{tableInfo.name}VO toVO($!{tableInfo.name}Entity entity) {
        if(Objects.isNull(entity)){
            return null;
        }
        $!{tableInfo.name}VO vo = new $!{tableInfo.name}VO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }

    public static $!{tableInfo.name}Entity toEntity($!{tableInfo.name}QueryDTO dto){
        if(Objects.isNull(dto)){
            return null;
        }
        $!{tableInfo.name}Entity entity = new $!{tableInfo.name}Entity();
        BeanUtils.copyProperties(dto , entity);
        return entity;
    }

    public static $!{tableInfo.name}Entity toEntity($!{tableInfo.name}InsertDTO dto) {
        if(Objects.isNull(dto)){
            return null;
        }
        $!{tableInfo.name}Entity entity = new $!{tableInfo.name}Entity();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    public static $!{tableInfo.name}Entity toEntity($!pk.shortType $!pk.name, $!{tableInfo.name}UpdateDTO dto) {
        if(Objects.isNull(dto)){
            return null;
        }

        $!{tableInfo.name}Entity entity = new $!{tableInfo.name}Entity();
        BeanUtils.copyProperties(dto, entity);
        entity.set${tool.firstUpperCase($pk.name)}($!{pk.name});
        return entity;
    }

    public static PageResult<$!{tableInfo.name}VO> toPageResult(IPage<$!{tableInfo.name}Entity> page) {
        List<$!{tableInfo.name}VO> list =  page.getRecords().stream().map($!{tableInfo.name}Assembler::toVO).collect(Collectors.toList());
        return new PageResult<$!{tableInfo.name}VO>(page.getTotal(),list);
    }
}