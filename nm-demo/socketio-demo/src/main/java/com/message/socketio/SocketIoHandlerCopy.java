//package com.message.socketio;
//
//import com.corundumstudio.socketio.AckRequest;
//import com.corundumstudio.socketio.SocketIOClient;
//import com.corundumstudio.socketio.SocketIOServer;
//import com.corundumstudio.socketio.annotation.OnConnect;
//import com.corundumstudio.socketio.annotation.OnDisconnect;
//import com.corundumstudio.socketio.annotation.OnEvent;
//import io.netty.util.internal.StringUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.UUID;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.ConcurrentSkipListSet;
//
///**
// * 消息处理类实现
// * 这时我模仿别人博客写的，但是我觉得这样写没有必要，博客地址在main方法那里有。
// */
//@Component
//public class SocketIoHandlerCopy {
//
//    @Autowired
//    private SocketIOServer socketIOServer;
//
//    /***
//     * 保存客户端信息
//     * <p>线程安全的map</p>
//     */
//    private static ConcurrentHashMap<String, ConcurrentSkipListSet> roomMap = new ConcurrentHashMap();
//
//    /**
//     * 监听客户端连接
//     * @param client 客户端对象
//     */
//    @OnConnect
//    public void onConnect(SocketIOClient client) {
//        System.out.println(client.getRemoteAddress() + "-------------------------" + "客户端已连接");
//
//        UUID sessionId = client.getSessionId();
//        String roomId = client.getHandshakeData().getSingleUrlParam("roomId");
//        if (StringUtil.isNullOrEmpty(roomId)) {
//            return;
//        }
//
//        ConcurrentSkipListSet<UUID> concurrentSkipListSet = roomMap.get(roomId);
//        if (concurrentSkipListSet == null || concurrentSkipListSet.isEmpty()) {
//            concurrentSkipListSet = new ConcurrentSkipListSet();
//        }
//        // 将客户端信息添加到集合中
//        concurrentSkipListSet.add(sessionId);
//        roomMap.put(roomId,concurrentSkipListSet);
//    }
//
//
//    /**
//     * 监听客户端断开
//     * @param client 客户端对象
//     */
//    @OnDisconnect
//    public void onDisconnect(SocketIOClient client) {
//        System.out.println(client.getRemoteAddress() + "-------------------------" + "客户端已断开连接");
//
//        UUID sessionId = client.getSessionId();
//        String roomId = client.getHandshakeData().getSingleUrlParam("roomId");
//        if (StringUtil.isNullOrEmpty(roomId)) {
//            return;
//        }
//        ConcurrentSkipListSet<UUID> concurrentSkipListSet = roomMap.get(roomId);
//        if (concurrentSkipListSet == null || concurrentSkipListSet.isEmpty()) {
//            return;
//        }
//        // 将客户端信息从集合中删除
//        concurrentSkipListSet.remove(sessionId);
//        roomMap.putIfAbsent(roomId, concurrentSkipListSet);
//    }
//
//    /**
//     * 监听名为roomMessageSending的请求事件
//     * @param client 客户端信息
//     * @param ackRequest 请求信息
//     * @param data 客户端发送数据，这里可以是一个对象的格式
//     */
//    @OnEvent(value = "roomMessageSending")
//    public void onEvent(SocketIOClient client, AckRequest ackRequest, String data) {
//        String roomId = client.getHandshakeData().getSingleUrlParam("roomId");
//        if (StringUtil.isNullOrEmpty(roomId)) {
//            return;
//        }
//
//        ConcurrentSkipListSet concurrentSkipListSet1 = roomMap.get(roomId);
//        // 取出客户端当前用户，并给该用户发送消息
//        ConcurrentSkipListSet<UUID> concurrentSkipListSet = roomMap.get(roomId);
//        if (concurrentSkipListSet == null || concurrentSkipListSet.isEmpty()) {
//            return;
//        }
//        // 给客户端发送消息，roomId这个客户，因为连接成功就将这个客户保存起来，发送信息是根据roomId这个从Map集合中获取客户端对象，然后在给他发送信息。
//        concurrentSkipListSet.forEach(x -> socketIOServer.getClient(x).sendEvent(roomId, data));
//    }
//
//    /**
//     * 主动推信息的思维
//     * 客户端连接和断开连接我们都可以获取到客户端的信息，这里将连接成功时，将客户端有用的信息保存到集合中，断开连接时
//     * 从集合中将客户端的信息删除，主动推信息时就可以在集合中获取到相应的客户端信息，我们就可以推信息了。
//     */
//
//
//    /**
//     * 指定用户标识发送信息
//     * @param roomId用户信息
//     * @param data 消息
//     */
//    public void onMessageSendOut(String roomId,String data){
//        if (roomId == null && data == null){
//            return;
//        }
//        // 取出客户端当前用户，并给该用户发送消息
//        ConcurrentSkipListSet<UUID> concurrentSkipListSet = roomMap.get(roomId);
//        if (concurrentSkipListSet == null || concurrentSkipListSet.isEmpty()) {
//            return;
//        }
//        // 给客户端发送消息，roomId这个客户，因为连接成功就将这个客户保存起来，发送信息是根据roomId这个从Map集合中获取客户端对象，然后在给他发送信息。
//        concurrentSkipListSet.forEach(x -> socketIOServer.getClient(x).sendEvent(roomId, data));
//    }
//}
