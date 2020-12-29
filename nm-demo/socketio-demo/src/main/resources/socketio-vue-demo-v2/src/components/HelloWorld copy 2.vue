<template>
  <div>
    <h1>{{ msg }}</h1>
    <button @click="connect">连接</button>
    <button @click="subscription">订阅</button>
    <button @click="callOffMsg">取消订阅</button>
  </div>
</template> 


<script>
import Socketio from "socket.io-client";
export default {
  name: "HelloWorld",
  data() {
    return {
      socketIoClient: null,
      msg: "Welcome to Your Vue.js App",
    };
  },
  methods: {
    connect() {
      /**
       * 这里的地址是后台服务器的地址，但是端口不是后端系统启动的真实端口，而是给socketIo指定的端口。
       */
      this.socketIoClient = Socketio.connect("http://192.168.124.7:9092");
      this.socketIoClient.on("connect", function () {
        console.log("连接了服务器");
      });
    },

    /**
     * 订阅text事件
     * 表示点击了订阅text，说明后端发送text这个事件的数据，我们就可以获取到。
     */
    subscription() {
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
  mounted: {},
};
</script>
