# small-study-case

# 一、项目说明

这是我个人开发的一个分布式项目，里面包含很多案例。因为这个分支保存的案例较多，单独拆分出来开源了。涉及保密的模块已经删除，`Git`历史版本已经取消。所以有些功能可能执行不了是正常的，有参考价值的部分我都会有相应的博客引入进来。我的博客地址：[https://blog.csdn.net/qq_41853447](https://blog.csdn.net/qq_41853447)



# 二、版本说明

例如：6.0.3.release

6：主版本号

当前的功能模块,有较大的变动,比如增加了一些模块,或者整个架构发生了改变了，表示对项目的改动很大。

`0`：次版本号，局部的变动，但是变动不是很大，可能与之前的版本不兼容。 

`3`：bug的修复

- 后面的字母表示当前处于什么开发阶段。

`Base`：当前处于设计阶段，没有功能实现。

`Alpha`：初级开发阶段，还存在很严重的bug。

`Beta`：相对Alpha有很大的改动，还在需要大量的测试。

`RELEASE`：最终版本。



# 三、将jar发布到本地仓库

## 3.1、发行讲解

### 命令

```shell
mvn install:install-file -Dfile=E:\security-oauth-1.0.0.Beta.jar -DgroupId=com.core -DartifactId=security-oauth -Dversion=1.0.0.Beta -Dpackaging=jar
```

> -Dfile  ：需要发布到本地仓库的jar包全绝对路径
>
> -DgroupId：jar包发行主体域名
>
> -DartifactId：产品分包
>
> -Dversion：版本号
>
> -Dpackaging：文件类型

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200711195917249.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70)

在本地仓库检查是否存在

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200711200140815.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70)

### idea工具

如果使用`Plugins`下面的`install`会报下面这个错误，说找不到这个打包插件。使用`lifecycle`下面的`install`问题得到解决。`lifecycle`是`maven`中一个十分完善的生命周期模型，所以使用`Lifecycle`中的`install`项目就会自动去`maven`仓库下载需要的包。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200711222104441.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200711221650698.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70)



```lua
Failed to execute goal org.apache.maven.plugins:maven-install-plugin:2.4:install (default-cli) on project security-oauth: The packaging for this project did not assign a file to the build artifact
```



## 3.2、这个jar编写pom.xml依赖注意

如果这个jar包的工程没有打成jar放到本地仓库或者私服引用，那么其他功能模块这个jar，这个jar应用的所有依赖都可以使用的。但是打成jar之后，放到本地仓库，在引入项目的这种方式，这个jar的依赖的那些依赖是不能使用。例如：在这个自定义的jar中依赖了链接数据库的所有东西，但是将这个jar引入进来之后，链接数据库的那些依赖只能在自定义这个jar中使用，不能再其他工程中使用了。



# 四、开发规范

### maven规范

- 除了根工程的pom.xml之外，子工程的pom.xml引入的jar都不予许设置version属性
- 基本的包版本控制主要由以下3个来控制

```
spring-boot-dependencies
platform-bom
spring-cloud-dependencies
```

- 新增加jar包依赖的步骤如下

  1. 先查看父工程/根工程是否已引入该jar
  2. 查看根工程的dependencyManagement是否已经有该jar的管理
  3. 如果没有则查看以下地址的Appendix A. Dependency versions 是否有该jar的管理

  ```
  https://docs.spring.io/platform/docs/Cairo-SR3/reference/htmlsingle/
  ```

  1. 都没有的话则自己添加该jar的管理，先去根工程的properties里添加版本号，再去dependencyManagement里添加该jar

### 接口规范

- 按照restful接口设计规范 GET （SELECT）：从服务器检索特定资源，或资源列表。

  POST （CREATE）：在服务器上创建一个新的资源。

  PUT （UPDATE）：更新服务器上的资源，提供整个资源。

  PATCH （UPDATE）：更新服务器上的资源，仅提供更改的属性。

  DELETE （DELETE）：从服务器删除资源。

- 接口尽量使用名词，禁止使用动词，下面是一些例子

```
GET         /zoos：列出所有动物园
POST        /zoos：新建一个动物园
GET         /zoos/{id}：获取某个指定动物园的信息
PUT         /zoos/{id}：更新某个指定动物园的信息（提供该动物园的全部信息）
PATCH       /zoos/{id}：更新某个指定动物园的信息（提供该动物园的部分信息）
DELETE      /zoos/{id}：删除某个动物园
GET         /zoos/{id}/animals：列出某个指定动物园的所有动物
DELETE      /zoos/{zId}/animals/{aId}：删除某个指定动物园的指定动物
```

- 反例：

```
/getAllCars
/createNewCar
/deleteAllRedCars
```

### 后端返回JSON

- 后端统一返回 com.central.common.model.Result 对象
  - datas：具体响应的其他信息
  - resp_code：响应码，目前0是成功、1是失败
  - resp_msg：响应消息

### 埋点日志规范

- 如果使用日志埋点的方式，建议按以下格式写日志

```
格式为：{时间}|{来源}|{对象id}|{类型}|{对象属性(以&分割)}

例子1：
2016-07-27 23:37:23|business-center|1|user-login|ip=xxx.xxx.xx&userName=张三&userType=后台管理员

例子2：
2016-07-27 23:37:23|file-center|c0a895e114526786450161001d1ed9|file-upload|fileName=xxx&filePath=xxx
```

### 各层命名规约

- `Service/DAO`层方法命名规约
  - 获取单个对象的方法用`get`做前缀。
  - 获取多个对象的方法用`list`做前缀。
  - 获取统计值的方法用`count`做前缀。
  - 插入的方法用`save/insert`做前缀。
  - 删除的方法用`remove/delete`做前缀。
  - 修改的方法用`update`做前缀。
- 领域模型命名规约
  - 数据对象：`xxxDO，xxx`即为数据表名。
  - 数据传输对象：`xxxDTO，xxx`为业务领域相关的名称。
  - 展示对象：`xxxVO，xxx`一般为网页名称。
  - `POJO`是`DO/DTO/BO/VO`的统称，禁止命名成`xxxPOJO`。