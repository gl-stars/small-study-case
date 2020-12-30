<center style="color:blue;font-weight:bold;font-size:40px">minio安装和使用</center>

官网：[https://min.io](https://min.io)

中文官网文档：[https://docs.min.io/cn/](https://docs.min.io/cn/)

英文官网文档：[https://docs.min.io](https://docs.min.io)

介绍参考：[https://www.jianshu.com/p/cbd1d8cac6f0](https://www.jianshu.com/p/cbd1d8cac6f0)

`minio` `GitHub`地址：[https://github.com/minio/minio](https://github.com/minio/minio)



练习源码地址：https://github.com/gl-stars/small-study-case/tree/master/nm-demo/classicsCase-demo/src/main/java/com/classics/controller

# 一、Docker环境下安装(单机)

参考：[https://docs.min.io/cn/minio-docker-quickstart-guide.html](https://docs.min.io/cn/minio-docker-quickstart-guide.html)

- 创建文件夹保存资源

```shell
mkdir -p /app/cloud/minio/data
mkdir -p /app/cloud/minio/config
# 给予权限
chmod -R 777 /app/cloud/minio/data
chmod -R 777 /app/cloud/minio/config
```



- 创建`minio`启动脚本

```shell
mkdir -p /opt/docker
vim /opt/docker/minio.sh
```

- 写入启动脚本数据

```shell
docker stop minio
docker rm minio
docker run -d -p 9000:9000 \
    --name minio \
    -e "MINIO_ACCESS_KEY=minio" \
    -e "MINIO_SECRET_KEY=Aa123456" \
    -v /app/cloud/minio/data:/data \
    -v /app/cloud/minio/config:/root/.minio \
    minio/minio server /data
```

> `MINIO_ACCESS_KEY`和 `MINIO_SECRET_KEY`相当于用户名和密码，上传资源时需要使用到。

- 启动脚本

因为我使用的是`docker`安装，所以不用直接去拉去镜像。我们可以直接启动这个脚本，会自动拉去镜像的。

```shell
sh /opt/docker/minio.sh
```



- 浏览器访问

注意需要将`9000`端口开放。

```
http://主机IP:9000
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201230165748772.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70)



# 二、`java`程序上传和删除资源

## 2.1、引入依赖

```xml
<dependency>
    <groupId>io.minio</groupId>
    <artifactId>minio</artifactId>
    <version>7.1.0</version>
</dependency>
```



## 2.2、创建对象保存资源相关信息

```java
package com.classics.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Minio Bucket访问策略配置
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class BucketPolicyConfigDto {

    private String Version;
    private List<Statement> Statement;

    @Data
    @EqualsAndHashCode(callSuper = false)
    @Builder
    public static class Statement {
        private String Effect;
        private String Principal;
        private String Action;
        private String Resource;

    }
}
```

```java
package com.classics.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文件上传返回结果
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MinioUploadDto {
    /***
     * 文件访问URL
     */
    private String url;
    /**
     * 文件名称
     */
    private String name;

    /***
     * 对象存储名称，删除资源的时候需要指定这个名称
     */
    private String objectName ;
}
```



## 2.3、上传资源类

```java
package com.classics.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文件上传返回结果
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MinioUploadDto {
    /***
     * 文件访问URL
     */
    private String url;
    /**
     * 文件名称
     */
    private String name;

    /***
     * 对象存储名称，删除资源的时候需要指定这个名称
     */
    private String objectName ;
}
```



## 2.4、配置`minio`相关信息

```yaml
minio:
  endpoint: http://服务器IP:9000 #MinIO服务所在地址
  bucketName: mall #存储桶名称
  accessKey: minio #访问的key
  secretKey: Aa123456 #访问的秘钥
```



## 2.5、存储路径分析

![在这里插入图片描述](https://img-blog.csdnimg.cn/2020123017124654.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70)



# 三、测试

## 3.1、上传资源

![在这里插入图片描述](https://img-blog.csdnimg.cn/2020123017052371.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70)



## 3.2、删除资源

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201230170723607.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70)



