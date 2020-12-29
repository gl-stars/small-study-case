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
      this.socketIoClient.on("connect", function () {
        console.log("连接了服务器");
      });
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
