# seata

参考博客：[https://www.pianshen.com/article/84721946457/](https://www.pianshen.com/article/84721946457/)

[https://www.jianshu.com/p/dec4550efbfc](https://www.jianshu.com/p/dec4550efbfc)

参考视频：[https://www.bilibili.com/video/BV12Q4y1A7Nt](https://www.bilibili.com/video/BV12Q4y1A7Nt)

<font style="font-weight: bold;color:blue;font-size:20px">特别注意：Seata目前只支持JDK1.8，我用jdk11、jdk14都不能正常运行，都报同一个gc的问题，估计是垃圾回收啥的。</font>

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201017150827656.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

# 一、下载

官网：[http://seata.io](http://seata.io/)

GitHub地址：[https://github.com/seata/seata](https://github.com/seata/seata)

## 1.1、下载

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201017205402663.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)



## 1.2、解决下载失败

因为这是国外的服务器，下载是是否缓慢的。有些时候快下载玩了，直接失败了。所以想下载是非常费力的，下面介绍一个网站，直接复制连接即可。

网址：[https://offcloud.com/#/instant](https://offcloud.com/#/instant)

```
邮箱：342591431@qq.com
密码：[通用密码]
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201017205905103.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201017205944273.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201017210156862.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/2020101721025886.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

这样就下载完成了

# 二、更改配置

## 2.1、复制文件和注入sql脚本到MySQL

在项目的 `nimble-make\nm-demo\springCloudAlibaba\spring-cloud-seata\file`文件下有。

### 复制文件

去`1.0`以前版本的`seata`复制一下四个文件，在官网也能寻找到。

<font style="font-weight: bold;color:blue">注：seata官方从1.0版本后不再提供sql脚本,以及nacos推送配置脚本,需要从0.9.0的版本复制</font>

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201017210712884.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

- 从官网寻找文件

`nacos-config.sh` 文件：  [https://github.com/seata/seata/tree/1.3.0/script/config-center/nacos](https://github.com/seata/seata/tree/1.3.0/script/config-center/nacos)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201017211822710.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

`nacos-config.txt`文件：[https://github.com/seata/seata/tree/1.3.0/script/config-center](https://github.com/seata/seata/tree/1.3.0/script/config-center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201017212113238.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

`db_undo_log.sql`sql文件 [https://github.com/seata/seata/tree/1.3.0/script/client/at/db](https://github.com/seata/seata/tree/1.3.0/script/client/at/db)

![在这里插入图片描述](https://img-blog.csdnimg.cn/2020101721234871.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

`db_store.sql`sql文件 [https://github.com/seata/seata/tree/1.3.0/script/server/db](https://github.com/seata/seata/tree/1.3.0/script/server/db)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201017212633294.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

### 注入sql脚本

`db_store.sql`中只有三张表，将这三张表保存道`MySQL`中的 `seata`这个数据库中。

`db_store.sql`这个是`seata`控制事务的时候需要使用到的。将这个`sql`放到`MySQL`的`seata`这个数据库下面，这个数据名是默认的，我们可以更改。但是一般我们都使用默认的。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201017211031524.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201017213015861.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

`db_undo_log.sql`这个sql文件中只有一张 `undo_log` 表，是专门保存事务的唯一`id`这些的，这张表需要在我们需要控制事务的每个数据库中，也就是说我们需要使用到全局事务的所有库中都需要添加这张表。目前我的案例中虽然有四个微服务，但是我连接的库都是同一个，所以我就在这个库中添加这张表。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201017213301440.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

## 2.2、更改配置文件

虽然我使用`JDK11`和`jdk14`都没有启动成功，但是我电脑是安装`JDK14`的，所以我是在虚拟机中安装测试的。配置文件我就在`Windows`上展示了，都一样的。

### 修改`file.conf`文件

文件在 `\seata\conf`目录下。

![在这里插入图片描述](https://img-blog.csdnimg.cn/2020101721343652.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/2020101721505468.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

<font style="font-weight: bold;color:blue">注意使用mysql8.0及以上版本的需要将驱动更改为：driverClassName = "com.mysql.cj.jdbc.Driver"</font>

查询`MySQL`版本：在`Navicat`中	`SELECT VERSION();`就可以查询到了。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201017215307696.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201017215157700.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

这里数据库的驱动并不难找到，以为我们项目中就连接过数据库，所以说就直接到我们本地仓库中就可以找到数据驱动的`jar`包了。寻找的时候，首先找到数据库驱动引入方式，在找到本地仓库，根据驱动引入的路径就可以寻找到。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201017215850697.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

### 修改`registry.conf`文件

这里主要有两个父节点 `registry`和 `config`，分别将这两个节点的的`type`动改为`nacos`，那么其余的注册中心配置就可以删除了。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201017220516145.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

更改后的文件

```lua
registry {
  # file 、nacos 、eureka、redis、zk、consul、etcd3、sofa
  type = "nacos"

  nacos {
    application = "seata-server"
    serverAddr = "192.168.164.128:8848"
    group = "SEATA_GROUP"
    namespace = ""
    cluster = "default"
    username = "nacos"
    password = "nacos"
  }
}

config {
  # file、nacos 、apollo、zk、consul、etcd3
  type = "nacos"

  nacos {
    serverAddr = "192.168.164.128:8848"
    namespace = ""
    group = "SEATA_GROUP"
    username = "nacos"
    password = "nacos"
  }
}
```

`application`为`seata`启动后注册到`nacos`的服务名(愿意改就改,不愿意改就默认就行)

需要注意：如果这里更改了，在程序中可能也要更改，应该要保持一致吧，没有测试过。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201017221115802.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)



<font style="font-weight: bold;color:blue">注:`group`默认为:`SEATA_GROUP`,更改`DEFAULT_GROUP`或者和自己服务相同的`group`,否则会报错`No available service`</font>

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201017221426529.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

### 修改 `nacos-conf.txt`文件

<font style="font-weight: bold;color:blue">注意：这个文件在seata1.0.0版本以后是不提供的，需要在之前版本中复制，2.1复制文件这里说的很清楚了。</font>

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201017221753574.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

```tex
service.vgroupMapping.my_test_tx_group=default
service.disableGlobalTransaction=false
store.mode=db
store.db.datasource=druid
store.db.dbType=mysql
store.db.driverClassName=com.mysql.jdbc.Driver
store.db.url=jdbc:mysql://www.it307.top:3306/seata?useUnicode=true
store.db.user=root
store.db.password=mallmysql
store.db.minConn=5
store.db.maxConn=30
store.db.globalTable=global_table
store.db.branchTable=branch_table
store.db.queryLimit=100
store.db.lockTable=lock_table
store.db.maxWait=5000
```

官网上写的很多，但是都有默认的，我们只需要关键这些就可以了。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201017222621169.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

### 将`nacos-conf.txt`文件发送到`nacos`注册中心

这时就要使用到 `nacos-config.sh`这个文件了，这个文件的来源 `2.1`小结也说了。这里要确保 ``nacos-conf.txt``文件和`nacos-config.sh`命令要在同一个文件夹下面，否则怎么知道你的问价在哪里呢？

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201017222954173.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

一般使用的命令

```shell
sh nacos-config.sh -h localhost -p 8848 -g SEATA_GROUP -t 5a3c7d6c-f497-4d68-a71a-2e5e3340b3ca -u username -w password
```

> -h：主机，默认值为localhost。
>
> -p：端口，默认值为8848。
>
> -g：配置分组，默认值为“ SEATA_GROUP”。
>
> -t：租户信息，对应于Nacos的名称空间ID字段，默认值为。
>
> -u：用户名，权限控制上的nacos 1.2.0+，默认值为。
>
> -w：密码，权限控制上的nacos 1.2.0+，默认值为“”。

这里因为是`Linux`命令，所以我们需要使用`git`的命令窗口，或者直接在`Linux`服务器上发送也行。

```shell
sh nacos-config.sh -h 192.168.164.128 -p 8848 -unacos -wnacos
```

> 其他默认值，我们可以不用带，这里的用户名密码和端口都可以不用带，这些都是默认值。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201017223450248.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

<font style="font-weight: bold;color:blue">注意：官方是说这样可以将配置文件推送到nacos的，但是我试了一直提示 `./nacos-config.sh nacosIp`，所以推不上去。</font>

```shell
sh nacos-config.sh 192.168.164.128
```

> 我直接这样推送，结果是没有问题的。可能是应为我是在Windows上使用`git`命令模拟操作的把。但是这样的没有关系，最后`nacos`数据持久化时都是采用`MySQL`存储，如果推不上去，那么我们就启动一个用户名和密码和端口等等都是默认的`nacos`，将数据推上去了，在从`MySQL`中将数据赋值到正式的nacos对应的`MySQL`库中。毕竟做持久化只需要配置一下就行了，不是很麻烦。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201017224837917.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201017225008641.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

这个配置如果不注入`nacos`控制台会不断答应 `no available service 'null' found, please make sure registry config correct`，找不到可用服务null，请确保注册表正确。

```lua
2020-10-18 00:06:29.848 ERROR 11884 --- [eoutChecker_2_1] i.s.c.r.netty.NettyClientChannelManager  : no available service 'null' found, please make sure registry config correct
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201018000828782.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

# 三、搭建seata

## 3.1、更改配置文件

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201017225507152.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201017225720922.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201017225927344.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201017230007617.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

## 3.2、启动

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201017232210830.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

启动的时候报了一个连接`MySQL`的异常，因为我们之前将 `nacos-config.txt`中的配置文件`nacos`中了，所以导致`seata`不能启动，但是 `nacos-config.txt`和`seata`这里的 `file.conf`和 `registry.conf`文件都没有配置错误。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201017232008670.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

- 解决方法

将 `nacos-config.txt`文件注入到`nacos`中的数据全部删除，在启动`seata`。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201017232612825.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)



- 在启动`seata`

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201017232735742.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![image-20201017232807913](C:\Users\stars\AppData\Roaming\Typora\typora-user-images\image-20201017232807913.png)



这里的服务名是在 `registry.conf`文件中配置的。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201018120047937.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

# 四、对接案例

## 4.1、整体业务流程梳理

我这里写了四个功能，分别是业务服务，订单服务，库存服务，账号服务。另外还有一个公共模块，因为之前的`seata`需要自己写一个定义一个数据源和代理，但是我这次是写了，但是没有使用，结果还是能够实现分布式事务的效果。

首先访问 `business-service`业务模块中的 `/placeOrder`和 `/placeOrderFallBack`接口，这两个接口其实都一样，只是传输的参数不同而已。这连个接口去调用库存服务 `storage-service`减库存，和订单服务处理订单 `order-service`。然后订单服务处理完订单后有调用账号服务 `account-service`去减少用户的余额等信息。但是如果用户等于 `U002`，那么我们就在账号服务中制造一个异常，让之前处理的全部数据回滚。



![在这里插入图片描述](https://img-blog.csdnimg.cn/20201017233217750.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/2020101723371719.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)



## 4.2、maven依赖说明

- 引入`seata`依赖

```xml
<!--seata-->
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-seata</artifactId>
    <!-- 将当期依赖中seata-all排除掉，重新引入 -->
    <exclusions>
        <exclusion>
            <artifactId>seata-all</artifactId>
            <groupId>io.seata</groupId>
        </exclusion>
    </exclusions>
</dependency>
<!-- 引入 seata -->
<dependency>
    <groupId>io.seata</groupId>
    <artifactId>seata-all</artifactId>
    <version>1.3.0</version>
</dependency>
```

为什么需要将里面的 `seata-all`排除掉重新引入，原因不太清楚。 `spring-cloud-starter-alibaba-seata`依赖的版本号是在 `spring-cloud-alibaba-dependencies`版本控制器里面定义好了。我们新引入的  `seata-all`依赖的版本号就和`seata`一样。`seata`我们使用哪个版本的，这里就使用哪个版本就OK。

只要涉及到分布式事务控制的时候，都需要这个依赖，因为里面涉及到一个`xid`分布式事务`id`的传递。这个功能目前不用我们担心了，但是可以去参考一下官方文档上的介绍。

这里因为测试的这几个工程都在同一个父工程下面，每个工程都需要连接数据库和事务这一块，我就将`seata`的`mybatisplus`和数据库驱动等依赖同一放在这几个工程的父`pom`中了。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201017234611832.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

其实每个工程下面还有一个`web`依赖和`nacos`注册与发现的依赖，这两个依赖完全可以放到父`pom`中的，不过不想更改了，就这样吧。

## 4.3、yml配置讲解

`bootstrap.yml`中配置`nacos`相关来接，`application.yml`在这里配置都一样，没有固定要求的。

![在这里插入图片描述](https://img-blog.csdnimg.cn/202010172351112.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/2020101723571557.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201018002717918.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201018000013311.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

## 4.4、测试

注意：这个`nacos-config.txt`文件我们之前注入`nacos`再去启动`seata`，提示`mysql`那边有些问题。所以我们将 `nacos-config.txt`注入到`nacos`的所有配置文件都删除了，现在还没有添加进去。如果现在启动微服务，是能够启动成功，但是只要遇到 `@GlobalTransactional`注解的地方就不能使用了。控制台会一直打印一下错误信息。

```lua
2020-10-18 00:10:09.438 ERROR 15876 --- [eoutChecker_2_1] i.s.c.r.netty.NettyClientChannelManager  : no available service 'null' found, please make sure registry config correct
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201018000828782.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

将`nacos-conf.txt`注册到`nacos`中，参考 `2.2`小结。

- 将四个微服务启动

没有顺序而言，随便起。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201018001353763.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

- 目前的数据状况

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201018001848305.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

- 正常流程

```http
http://localhost:9090/placeOrder
```

- 异常请求，数据回滚

```http
http://localhost:9090/placeOrderFallBack
```

请求一下正常流程

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201018004810803.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/2020101800473711.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

> 这个结果是满意的，因为没有出现任何差错。

请求一次异常，然后在观察一下数据，数据是否回滚。

![在这里插入图片描述](https://img-blog.csdnimg.cn/2020101800493774.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201018005040769.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201018005227320.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

# 五、seata集群

集群，其实可以多启动几个seata到注册中心就可以了，但是另外还有一个根据事务组来隔离，应该可以说成隔离吧。

像nacos注入多个`seata`，可以在命令后面添加 `-p 端口号`。

```http
sh seata-server.sh -p 8094
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201018120139915.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)



- 使用事务分组

在 `registry.conf`配置文件中 `nacos.cluster`名称更改为集群名称，在将 `nacos-config.txt`文件中的 `service.vgroupMapping.my_test_tx_group=testCluster`更改为对应的集群明名称，因为这个是注入`nacos`中的，直接在`nacos`中更改也可以。最后在项目的配置文件中指定事务组 `tx-service-group: my_test_tx_group`，这样就搞定了。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201018115530647.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

- 启动项目

这里的使用群组的方式集群，不知道哪里还有些小毛病没有成功。















# 常见的问题

```lua
io.seata.common.exception.FrameworkException: can not connect to services-server.
```

提示无法连接到 `services-server`服务器，一般需要注意一下配置。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201018003514369.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201018003639742.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201018003728551.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201018005528634.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

其次就检查，是不是部署的`seata`挂了。我目前的状态就是部署号的`seata`关闭了。

