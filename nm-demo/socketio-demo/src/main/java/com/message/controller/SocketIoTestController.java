package com.message.controller;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.message.socketio.SocketIoHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class SocketIoTestController {

    @Autowired
    private SocketIOServer socketIOServer;

    @Autowired
    private SocketIoHandler socketIoHandler;

    /**
     * 广播的形式发送消息，只要订阅指定事件的都能查看到。
     * @return
     */
    @RequestMapping(value = "/broadcast")
    public void broadcast() {
        //广播式发送消息
        //向所有订阅了名为text事件的客户端发送消息
        Collection<SocketIOClient> allClients = socketIOServer.getAllClients();
        allClients.parallelStream().forEach(x -> {
            x.sendEvent("text", "Hello socketIo!");
        });
    }


    /**
     * 指定用户发送信息
     * @param roomId 用户标识
     * @return
     */
    @GetMapping("/room/id")
    public String sendOut(String roomId,String data){
        socketIoHandler.onMessageSendOut(roomId,data);
        return "KO";
    }

}
