<template>
  <div>
    <h1>{{ msg }}</h1>
    <button @click="connect">连接</button>
  </div>
</template> 


<script>
import Socketio from "socket.io-client";
export default {
  data() {
    return {
      socketIoClient: null,
      msg: "Welcome to Your Vue.js App",
    };
  },
  methods: {
    /**
     * 这个方法是上面“连接”这个按钮出发的。
     */
    connect() {
      this.socketIoClient = Socketio.connect("http://192.168.124.7:9092");
      //监听与服务器的连接状态
      this.socketIoClient.on("connect", function () {
        console.log("连接了服务器");
        this.msg = "连接OK";
      });
    },
  },
  mounted: {},
};
</script>
