# socketio实现方式

# 一、后端实现

## 1.1、基本实现

- 导入依赖

```xml
<dependency>
    <groupId>com.corundumstudio.socketio</groupId>
    <artifactId>netty-socketio</artifactId>
    <version>1.7.11</version>
</dependency>
```

- SocketIo配置

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200902154800873.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

- SocketIo启动

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200902154927491.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

- 消息实现类

```
SocketIoHandler
```

下面详细说明

后端大概就这些代码了，很简洁的，多的就没有。

## 1.2、消息处理类

- 监听客户端连接

客户端连接成功是调用。

![在这里插入图片描述](https://img-blog.csdnimg.cn/2020090215514167.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

在方法上面打上 `@OnConnect`注解，表示监听连接情况，这个方法传入 `SocketIOClient`对象，获取连接这个客户端的信息。

这里最主要获取客户端的 `sessionID`和客户的唯一标识，其实这个`sessionID`就是一个 `java.util.UUID`对象，但是指定用户发送信息是需要用到，所以在这里就拿到它并保存到`Map`集合中。

这里我们做的不多，就是获取一个`UUID`和用户唯一标识。这些都是`String`类型的，如果用户量比较大的情况下，我们可以存入`Redis`中，不要存在这个`Map`集合了。

- 监听客户端断开

客户端断开连接时调用。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200902155741978.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

在方法上打上 `@OnDisconnect`注解，表示监听客户端是否断开。同样需要 `SocketIOClient`对象，当客户端断开时可以获取到`sessionID`和用户唯一标识等客户端信息。但是我觉得只要获取到用户唯一标识就足够了，因为`sessionID`在连接成功就拿到并保存了的。

客户端断开了，获取到客户唯一标识并在保存客户端信息这里面删除，这里是保存在`map`集合中，那么就在这个集合中删除就OK。删除就标识不在线啦。

- 监听客户端名为某个事件的信息

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200902160243827.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

客户端代码应该是这样的。

```json
socket.emit("事件名","参数数据")方法，是触发后端自定义消息事件的时候使用的
```

当事件名为 `roomMessageSending`这个时发送的数据就会在这里被调用，这里也可以获取到客户端的相关信息，和客户端传过来的数据。这个事件名是随便写的，数据也可以是其他形式的，比如对象 ，前端平时对象是怎么赋值的就怎么赋值。

- 给指定的客户发送信息

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200902160622375.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

关键就是这一句

```java
// 获取对象
@Autowired
private SocketIOServer socketIOServer;


socketIOServer.getClient(roomMap.get(roomId)).sendEvent(roomId, data) ;
```

`roomMap.get(roomId)`根据你要发送信息给哪个客户，那么就将这个客户的唯一标识穿过来，利用这个唯一标识就可以在`Map`集中获取`sessionID`，就是在连接成功时获取的那个`UUID`，

`sendEvent(roomId, data)`，`roomId`客户的唯一标识，`data`需要穿的数据，当然也可以是其他数据类型。



# 二、前端实现

安装 `socket.io`

```lua
npm install --save socket.io-client
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200902161527126.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

- 引入`socket` 

```
import sio from "socket.io-client";
```

- 注意这两个生命周期

```vue
  /**
   * 加载组件执行
   * 是数据挂载后的生命周期函数
   */
  mounted() {
    this.connect();
  },
  /**
   * 关闭组件执行
   * 实例销毁后
   */
  destroyed() {
    this.socketIoClient.close();
  },
```

`mounted()`数据已经挂在到模板中，这时执行 `connect()`这个方法，让`socketIo`的信息，并将这个对象保存，方便后面使用。



![在这里插入图片描述](https://img-blog.csdnimg.cn/20200902162515234.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

- 完整代码

```vue
<template>
  <div>
    <h2 ref="SendMsg">
      <ul>
        <li v-for="(item, index) in itemArr" :key="index">{{ item }}</li>
      </ul>
    </h2>
    <input type="text" v-model="msg" />
    <button @click="sendMsg">发送</button>
  </div>
</template> 

<script>
import sio from "socket.io-client";
export default {
  name: "HelloWorld",
  data() {
    return {
      socketIoClient: null,
      itemArr: ["欢迎来到WIM1000聊天室"],
      roomId: "WIM1000",
      msg: "",
    };
  },
  methods: {
    connect() {
      this.socketIoClient = sio.connect(
        "http://192.168.124.7:9092?roomId=" + this.roomId
      );
      // 服务器连接成功调用
      this.socketIoClient.on("connect", function () {
        console.log("连接了服务器");
      });
      // 监听当前用户唯一标识的这个事件名，用户发送信息就靠这个事件名来接收。
      this.subscription();
    },
    subscription() {
      let vm = this;
      //   socket.on("事件名",匿名函数(服务器向客户端发送的数据))为监听服务器端的事件
      this.socketIoClient.on(this.roomId, function (data) {
        // 这是服务器端发送的信息
        console.log(data);
        vm.itemArr.push(data);
      });
    },
    sendMsg() {
      // socket.emit("事件名","参数数据")方法，是触发后端自定义消息事件的时候使用的,
      this.socketIoClient.emit("roomMessageSending", this.msg);
    },
  },
  /**
   * 加载组件执行
   * 是数据挂载后的生命周期函数
   */
  mounted() {
    this.connect();
  },
  /**
   * 关闭组件执行
   * 实例销毁后
   */
  destroyed() {
    this.socketIoClient.close();
  },
};
</script>
```



# 三、测试效果

- 整个过程分析

`this.socketIoClient.on(this.roomId, function (data) `这里监听的是当前自己这个用户的，因为是用自己的唯一标识作为监听函数名的。

`socketIOServer.getClient(roomMap.get(roomId)).sendEvent(roomId, data) ;`那个用户发来的数据就将客户的唯一标识作为监听函数推数据过去。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200902165107747.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)



- 测试

因为我前端部署了两套一模一样的，模拟两个人。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200902165435389.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

将前后端都启动起来。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200902165537158.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200902165655291.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200902170009420.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200902170123198.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)



# 四、业务设计描述

如果在线人数非常多的情况下，后天将用户登录的`sessionID`就是那个`UUID`和用户的唯一标识保存在`Map`集合中是不现实的，建议保存在`Redis`中。用户唯一标识可以作为`key`，回话`UUID`作为`value`。当设置用户表的时候，用户`ID`用分布式ID策略生成。前端登录成功后就可以获取到自己的`ID`，然后在前端就可以作为自己的用户唯一标识了。当登录成功时，可以将所有在线的客户存入一个`Redis`库中，需要使用时就直接在这个`Redis`库中查询。客户单连接失败了，就说明已经下线了，就在这张`Redis`库中删除用户信息。但是需要注意，如果同一个用户在不同机器上登录呢？用户的这个唯一标识是不是不能直接作为`Redis`的`key`，这个`key`需要在加工处理。

但是当其他用户想要给没有在线的用户发送信息，这时我们发现目标用户已经不在线了。可以将需要发送的这些信息保存到消息队列中，当目标用户上线我们在发送给目标用户。



# 五、广播式发送消息

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200902171707493.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

今后可以将订阅的事件名和数据都写成动态的，用户需要开启什么监听直接传入动态的数据进来就行了。关键订阅了这个事件名保存在哪里呢？跟踪代码后发现，最终这里的订阅的事件名都是保存在集合中的，所以不合适开启的订阅事件名过多。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200902172542393.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200902171940641.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

- 测试

将两个客户端你都启动，但是一个都不订阅的情况下，在后端发送一个`text`订阅。两个客户端都没有收到任何数据，说明OK。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200902173003150.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

将客户唯一标识为 `WIM2000`的开启订阅，在后端发送一个订阅请求。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200902173400323.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

将两个都开启订阅，这样两个都收到`text`的订阅信息。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200902173542619.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxODUzNDQ3,size_16,color_FFFFFF,t_70#pic_center)

噢啦，暂时就讲解到这里。