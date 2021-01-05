Spring 统一验证

[hibernate-validator](https://docs.jboss.org/hibernate/validator/5.1/reference/en-US/html_single/) 字段格式验证





### 现实场景

避免重复提交

后端根据请求的payload和其他参数来确定唯一，uriPath+userId+MD5(JsonString(所有参数))作为key,用redis分布式锁

防止重复提交完全由后端控制，前端无感，不能做在filter中，因为request payload只能被消费一次。可以用spring aop来实现，对resource method 做aop拦截。