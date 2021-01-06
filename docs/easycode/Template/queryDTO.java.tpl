##引入宏定义
$!define
##定义初始变量
#set($packageName = $tool.append($tableInfo.savePackageName,".",$tool.firstLowerCase($tableInfo.name)))
#set($className = $tool.append($tableInfo.name, "QueryDTO"))
#set($fillOnInsertColumn = $tool.newArrayList("deleted","create_time","create_user","update_time","update_user"))
#set($fillOnUpdateColumn = $tool.newArrayList("update_time","update_user"))
#set($versionColumn = "update_time")
#set($logicDeleteColumn = "deleted")
##设置回调
#save($tool.append("/", $tool.firstLowerCase($tableInfo.name),"/service/dto"), "QueryDTO.java")

## package
#if($packageName)package $!{packageName}#{end}.service.dto;

$!autoImport
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

##使用宏定义实现类注释信息
#tableComment("QueryDTO")
@ApiModel("$!{className}")
@Getter
@Setter
@Builder
@NoArgsConstructor
public class $!{className} {
#foreach($column in $tableInfo.otherColumn)
    #if(!($fillOnInsertColumn.contains(${column.obj.name})
        || $fillOnUpdateColumn.contains(${column.obj.name})
        || $versionColumn.equals(${column.obj.name})
        || $logicDeleteColumn.equals(${column.obj.name})))
    #if(${column.comment})
    /**
     * ${column.comment}
     */#end
    @ApiModelProperty("${column.comment}")
    private $!{tool.getClsNameByFullName($column.type)} $!{column.name};
    #end
#end
}