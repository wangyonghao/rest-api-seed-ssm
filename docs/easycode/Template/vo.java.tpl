##引入宏定义
$!define
##定义初始变量
#set($packageName = $tool.append($tableInfo.savePackageName,".",$tool.firstLowerCase($tableInfo.name)))
#set($entityName = $tool.append($tableInfo.name, "VO"))
#set($fillOnInsertColumn = $tool.newArrayList("deleted","create_time","create_user","update_time","update_user"))
#set($fillOnUpdateColumn = $tool.newArrayList("update_time","update_user"))
#set($versionColumn = "update_time")
#set($logicDeleteColumn = "deleted")
##设置回调
$!callback.setFileName($tool.append($entityName, ".java"))
$!callback.setSavePath($tool.append($tableInfo.savePath, "/", $tool.firstLowerCase($tableInfo.name),"/service/vo"))
## package
#if($packageName)package $!{packageName}#{end}.service.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

$!autoImport
import java.io.Serializable;

##使用宏定义实现类注释信息
#tableComment("VO")
@ApiModel("$!{tableInfo.obj.name}")
public class $!{entityName} implements Serializable {
#foreach($column in $tableInfo.fullColumn)
    #if(${column.comment})/**
    * ${column.comment}
    */#end
    @ApiModelProperty("${column.comment}")
    private $!{tool.getClsNameByFullName($column.type)} $!{column.name};

#end

#foreach($column in $tableInfo.fullColumn)
##使用宏定义实现get,set方法
#getSetMethod($column)
#end

}