package com.message.socketio;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 消息处理类实现
 */
//@Component
public class SocketIoHandler {

    @Autowired
    private SocketIOServer socketIOServer;

    /***
     * 保存客户端信息
     * <p>线程安全的map</p>
     */
    private static ConcurrentHashMap<String, UUID> roomMap = new ConcurrentHashMap();

    /**
     * 监听客户端连接
     * @param client 客户端对象
     */
    @OnConnect
    public void onConnect(SocketIOClient client) {
        System.out.println(client.getRemoteAddress() + "-------------------------" + "客户端已连接");
        // 获取sessionID
        UUID sessionId = client.getSessionId();
        // 获取到客户端唯一标识，这个可以有后端返回。
        String roomId = client.getHandshakeData().getSingleUrlParam("roomId");
        // 将sessionID和客户端唯一标识存入Map集合中
        roomMap.put(roomId,sessionId);
    }


    /**
     * 监听客户端断开
     * @param client 客户端对象
     */
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        System.out.println(client.getRemoteAddress() + "-------------------------" + "客户端已断开连接");
        // 获取客户端唯一标识
        String roomId = client.getHandshakeData().getSingleUrlParam("roomId");
        roomMap.remove(roomId);
    }

    /**
     * 监听名为roomMessageSending的请求事件
     * @param client 客户端信息
     * @param ackRequest 请求信息
     * @param data 客户端发送数据，这里可以是一个对象的格式
     */
    @OnEvent(value = "roomMessageSending")
    public void onEvent(SocketIOClient client, AckRequest ackRequest, String data) {
        // 获取用户唯一标识
        String roomId = client.getHandshakeData().getSingleUrlParam("roomId");
        if (StringUtil.isNullOrEmpty(roomId)) {
            return;
        }
        // 根据客户唯一标识从map中获取客户端sessionID，其实就是一个UUID。
        socketIOServer.getClient(roomMap.get(roomId)).sendEvent(roomId, data) ;
    }

    /**
     * 指定用户标识发送信息
     * @param roomId 用户信息
     * @param data 消息
     */
    public void onMessageSendOut(String roomId,String data){
        if (roomId == null && data == null){
            return;
        }
        // 获取到需要发送过去的那个客户唯一标识，在根据这个标识去map中获取到sessionID，在发送信息。
        socketIOServer.getClient(roomMap.get(roomId)).sendEvent(roomId, data) ;
    }
}
