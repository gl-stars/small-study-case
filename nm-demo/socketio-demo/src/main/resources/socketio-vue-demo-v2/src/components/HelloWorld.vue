<template>
  <div>
    <h2 ref="SendMsg">
      <ul>
        <li v-for="(item, index) in itemArr" :key="index">{{ item }}</li>
      </ul>
    </h2>
    <input type="text" v-model="msg" />
    <button @click="sendMsg">发送</button>
    <button @click="subscriptionAll">订阅</button>
    <button @click="callOffMsg">取消订阅</button>
  </div>
</template> 

<script>
import sio from "socket.io-client";
export default {
  name: "HelloWorld",
  data() {
    return {
      socketIoClient: null,
      itemArr: ["欢迎来到WIM2000聊天室"],
      roomId: "WIM2000",
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

    /**
     * 订阅text事件
     * 表示点击了订阅text，说明后端发送text这个事件的数据，我们就可以获取到。
     */
    subscriptionAll() {
      console.log("订阅了text事件");
      this.socketIoClient.on("text", function (data) {
        console.log(data);
      });
    },

    /**
     * 取消text事件订阅
     * 取消后端发送text的事件的数据。
     */
    callOffMsg() {
      console.log("取消了事件的订阅");
      this.socketIoClient.removeAllListeners("text");
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
