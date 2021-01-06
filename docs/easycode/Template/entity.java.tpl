##引入宏定义
$!define
##定义初始变量
#set($packageName = $tool.append($tableInfo.savePackageName,".",$tool.firstLowerCase($tableInfo.name)))
#set($className = $tool.append($tableInfo.name, "Entity"))
#set($fillOnInsertColumn = $tool.newArrayList("deleted","create_time","create_user","update_time","update_user"))
#set($fillOnUpdateColumn = $tool.newArrayList("update_time","update_user"))
#set($versionColumn = "update_time")
#set($logicDeleteColumn = "deleted")
##设置回调
$!callback.setFileName($tool.append($className, ".java"))
$!callback.setSavePath($tool.append($tableInfo.savePath, "/", $tool.firstLowerCase($tableInfo.name),"/dao/entity"))
## package
#if($packageName)package $!{packageName}#{end}.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

$!autoImport
import java.io.Serializable;

##使用宏定义实现类注释信息
#tableComment("实体类")
@TableName("$!{tableInfo.obj.name}")
public class $!{className} implements Serializable {
#foreach($column in $tableInfo.pkColumn)
    #if(${column.comment})/**
    * ${column.comment}
    */#end
    @TableId(value = "${column.obj.name}", type = IdType.ASSIGN_ID)
    private $!{tool.getClsNameByFullName($column.type)} ${column.name};
#end
#foreach($column in $tableInfo.otherColumn)
    #if(${column.comment})/**
    * ${column.comment}
    */#end
    #if($fillOnInsertColumn.contains($column.obj.name) && $fillOnUpdateColumn.contains($column.obj.name))
    @TableField(value = "${column.obj.name}", fill = FieldFill.INSERT_UPDATE)
    #elseif($fillOnInsertColumn.contains($column.obj.name))
    @TableField(value = "${column.obj.name}", fill = FieldFill.INSERT)
    #elseif($fillOnUpdateColumn.contains($column.obj.name))
    @TableField(value = "${column.obj.name}", fill = FieldFill.UPDATE)
    #else
    @TableField(value = "${column.obj.name}")
    #end
    #if($logicDeleteColumn.equals($column.obj.name))
    @TableLogic
    #end
    #if($versionColumn.equals($column.obj.name)) 
    @Version 
    #end
    private $!{tool.getClsNameByFullName($column.type)} ${column.name};
#end

#foreach($column in $tableInfo.fullColumn)
    ##使用宏定义实现get,set方法
    #getSetMethod($column)
#end

}