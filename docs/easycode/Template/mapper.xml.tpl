##引入mybatis支持
$!mybatisSupport
##定义初始变量
#set($packageName = $tool.append($tableInfo.savePackageName, ".", $tool.firstLowerCase($tableInfo.name)))
##设置保存名称与保存位置
$!callback.setFileName($tool.append($!{tableInfo.name}, "Mapper.xml"))
$!callback.setSavePath($tool.append($modulePath, "/src/main/resources/mapper"))

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="$!{packageName}.dao.$!{tableInfo.name}Mapper">

</mapper>
