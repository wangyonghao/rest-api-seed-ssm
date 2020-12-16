## RESTful API 规范

## 前言

##### 如何设计和开发一个高可用的 REST APIs

网上一直有关于"最好的Restful API的设计"争论，何为最好，至今没有一个官方的指导。本文总结 RESTful 的设计细节，介绍如何设计出易于理解和使用的 API。在 Restful API 设计标准之上，我们可以为我们的设计增加一些弹性（团队都认可的方式），每个项目的情况不同，最重要的是项目组成员达成一致的Restful API 设计规则，达到高可用即可。

[Wikipedis - REST](https://en.wikipedia.org/wiki/Representational_state_transfer)

[如何设计好的RESTful API](https://mp.weixin.qq.com/s/hR1TqkVzwZ_T8fuMnsM4hQ)

## 一、URL 设计篇

### 1. 动词 + 名词

#### 动词

动词通常就是 5 种 HTTP 方法，对应我们常见的 CRUD 操作：

- POST：新建（Create）
- GET：读取（Read）
- PUT：更新（Update）
- PATCH：更新（Update），通常不分更新，也很少用到
- DELETE：删除（Delete）

根据 HTTP 规范，动词一律大写，另外根据RESTful 幂等性（多次调用是否会对资源产生影响）原则，我们不能乱用动词，GET/PUT/DELETE 是幂等的，POST/PATCH 不是幂等的。

#### 名词

名词就是表示一个资源或者服务，如 /users，/teachers，这里看到我用名词复数的形式描述某一资源，至于用单数还是复数每个人都有自己的见解，我在这里推荐使用复数，因为在现实世界中，资源多数是以集合的形式存在的

#### 动词 + 名词

- `POST /users` 创建新用户
- `GET /users` 查询所有用户
- `PUT /users` 批量更新用户
- `DELETE /users` 批量删除用户
- `GET /users/12` 查询指定用户
- `PUT /users/12` 更新指定用户
- `DELETE /users/12` 删除指定用户

#### 其他

除了以上常用的动作，我们还会遇到导入、导出、上载、下载等场景

- `GET /users/export/user.xlsx` 导出用户数据，文件名为user.xlsx
- `POST /users/import` 使用Excel文件导入用户数据

#### URL 层级

现实中哪有这么简单的 CRUD，资源的相互关联与嵌套很常见，查找 id 是 12 的用户的所有帖子， 如何设计这个 URL，下面两种设计也会有争论：

```
GET /users/12/postsGET 
GET /posts?userId=12
```

第一种出现两个名词主题（users/posts），会让人有几秒钟的猜想，这到底请求的是用户资源还是帖子资源，当存在更深浅套的时候也不容易扩展，所以我推荐第二种方式，主体名词 posts 资源明显，其他过滤条件也更容易扩展，比如 /posts?userName=zhangsan，我们可以复用同样的接口

### 过滤/分页/排序

实际的业务场景中会经常对请求资源做条件筛选，分页显示，以及排序，我们不要为这些业务要求创建不同步的 API，我们应该尽量保持 URL 的信息简单，只需添加查询条件参数来实现上述功能，同时符合"望 URL 知意"的原则

#### 过滤

```
GET /users/12/posts?state=published
GET /users/12/posts?published=true
```

#### 分页

```
GET /users?page=2&perpage=20
```

以分页方式查询用户列表，显示第 2 页内容，每页显示 20 条信息

#### 排序

```
GET /users?sort=score_desc
```

按照学生分数降序进行排序



### 版本管理

我们看到过很多如下 URL 设计，用来区分 API 版本：

```
POST /v2/usersGET /V1/users/12
```

我们都指向同样的资源 users，URL 中为什么要加版本号呢？ 针对这个问题，答案依旧没有统一标准，如果多个版本的API版本返回数据结果结构一样，那没必要区分版本，如果结构已经发生变化，而且要向下兼容，那版本号是很好的区分方式，而且通过 URL 加版本的方式可以更好的发现资源

## 二、HTTP篇

### 1. 请求

#### 无状态会话

过去开发人员通常会将活动的用户信息存储在服务端的 session 中，这种形式很显然不适用于现在分布式架构的模式，我们可以使用 JWT (JSON Web Token) 如 OAuth2 来实现，这样每次在 HttpHeader 中添加 token 来做验证即可

### 2. 响应

#### 响应数据格式

API 返回的数据格式，不应该是纯文本，而应该是一个 JSON 对象，因为这样才能返回标准的结构化数据。所以，服务器回应的 HTTP 头的 `Content-Type`属性要设为 `application/json`。同时客户端也应作出相应的配合，客户端请求时，也要明确告诉服务器，可以接受 JSON 格式，即请求的 HTTP 头的 `ACCEPT`属性也要设成 `application/json`，多渠道调用可能会存在相同资源需要有不同的 producer 类型的情况存在

#### 响应状态码

##### 2xx 状态码

200表示成功，同时我们可以表示的更加精确

> - `GET:200OK` 请求成功
> - `POST:201Created` 创建成功
> - `PUT:200OK` 更新成功
> - `DELETE:204NoContent` 找不到要删除的内容

使用状态码 202 有时候会比 使用状态啊吗 201 是更好的选择，状态码 202 的意思是：服务端已接收到了请求，但是还没有创建任何资源，但结果一切正常。 比如：

> - 异步操作：服务器已接收到请求，但是还未处理，但是会在未来处理
> - 资源已经存在，没有创建新的资源 （有些业务可能会返回错误信息"您创建的数据已存在"，所以这种情景没有明确的规定，符合自己的业务需求即可）

##### 4xx 状态码

4xx 状态码表示客户端的错误，主要有以下几种：

> - `400BadRequest`：服务器不理解客户端的请求，未做任何处理
> - `401Unauthorized`：用户未提供身份验证凭据，或者没有通过身份验证
> - `403Forbidden`：用户通过了身份验证，但是不具有访问资源所需的权限
> - `404NotFound`：所请求的资源不存在，或不可用
> - `415UnsupportedMediaType`：客户端要求的返回格式不支持。比如，API 只能返回 JSON 格式，但是客户端要求返回 XML 格式

这里要注意状态码 401 和 403 的区别

##### 5xx 状态码

5xx 状态码表示服务端错误，通常只会用到两个：

> - `500InternalServerError`：客户端请求有效，服务器处理时发生了意外
> - `503ServiceUnavailable`：服务器无法处理请求，一般用于网站维护状态

## 三、文档篇

### Swagger

Swagger是一种广泛使用的工具来用来记录与呈现 REST API，它提供了一种探索特定 API 使用的方法，因此允许开发人员理解底层的语义行为。 这是一种使用注释添加文档的声明性方法，它进一步生成描述 API 及其用法的 JSON，可以实时应对 API 的更新，具体请参考 Swagger 官网 , 同时使用 Spring Boot 的小伙伴也可以很轻松的集成 Swagger，只需引入Swagger Starter

```xml
<dependency>    
  <groupId>com.spring4all</groupId>    
  <artifactId>swagger-spring-boot-starter</artifactId>    
  <version>1.9.0.RELEASE</version>
</dependency>
```

## 四、测试篇

### Postman

Postman 功能十分强大， 搜索 `Postman自定义环境变量`，会打开新世界的大门



参考：

