package com.message.socketio;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 示范将连接的数据保存到redis中，这里为了示范，我就模拟操作，并不真实的保存。
 * @author: gl_stars
 * @data: 2020年 10月 22日 09:27
 **/
@Component
public class SocketIoCanonicalHandler {

    @Autowired
    private SocketIOServer socketIOServer;

    /***
     * 保存客户端信息
     * <p>
     *     这里的UUID主要由leastSigBits和mostSigBits组成。我们获取UUID的时候主要将这两个值保存到redis，到时候直接从redis中获取这两个值就可以转换为UUID。
     *     这样就可以实现将用户连接信息保存到redis的效果了。
     * </p>
     * <p>线程安全的map</p>
     */
    private static ConcurrentHashMap<String, UUID> roomMap = new ConcurrentHashMap();

    /**
     * 监听客户端连接
     * @param client 客户端对象，这里可以获取到客户端的相关信息。比如token等
     */
    @OnConnect
    public void onConnect(SocketIOClient client) {
        String token = client.getHandshakeData().getSingleUrlParam("token");
        System.out.println(client.getRemoteAddress() + "-------------------------" + "客户端已连接");
        // 获取sessionID
        UUID sessionId = client.getSessionId();
        long leastSignificantBits = sessionId.getLeastSignificantBits();
        long mostSignificantBits = sessionId.getMostSignificantBits();

        // 模拟将UUID保存到redis

        // 获取到客户端唯一标识，这个可以有后端返回。
        String roomId = client.getHandshakeData().getSingleUrlParam("roomId");
        // 将sessionID和客户端唯一标识存入Map集合中
        roomMap.put(roomId,sessionId);
    }

    /***
     * 保存UUID
     * @param id 用户编号
     * @param leastSigBits UUID中的leastSigBits变量
     * @param mostSigBits UUID 中的mostSigBits变量
     */
    public void setUUID(Long id,Long leastSigBits,Long mostSigBits){

        // 模拟将UUID保存到redis中，但是当前工程我没有集成redis，所以这只是一个模拟。
//        redis.set("User_key_id",new UUIDSave(leastSigBits, mostSigBits));
    }
    /**
     * 这里我就懒得创建一个保存的对象了，直接在这里创建一个内部类解决了吧。
     */
    class UUIDSave{
        // UUID中的leastSigBits变量
        Long leastSigBits ;
        // UUID 中的mostSigBits变量
        Long mostSigBits ;

        UUIDSave(Long leastSigBits,Long mostSigBits){
            this.leastSigBits = leastSigBits;
            this.mostSigBits = mostSigBits;
        }
    }
    /***
     * 根据 leastSigBits 和 mostSigBits 获取UUID。
     * @param leastSigBits
     * @param mostSigBits
     * @return
     */
    public UUID getUUID(Long leastSigBits,Long mostSigBits){
        UUID uuid = new UUID(mostSigBits,leastSigBits);
        return uuid ;
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

    /***
     * 根据UUID发送给指定的用户
     * <p>
     *     {@link SocketIoCanonicalHandler#onMessageSendOut(java.lang.String, java.lang.String)}这个方法是
     *     用户必须监听不同的 roomId 事件，我这边分别发送给监听 roomId 值事件的指定用户，而我这个方法是只要用监听UserSocket事件，这个事件是写成死的
     *     我就可以根据UUID发送消息。因为每个人的UUID不一样，我可以根据UUID指定客户端发送。而上面那种也是根据UUID发送的，但是监听的事件名是动态的，不同的用户
     *     监听的事件不同，我后端首先要知道用户的UUID，然后还要观察当前用户监听的事件名是啥。而我目前的实现方式，根据用户的UUID就能确定客户端的，直接发送给监听UserSocket事件的
     *     客户端就可以了。并且上面我还做了，获取到连接用户的UUID，并且将UUID拆分为两个Long类型的常量保存到redis，然后需要使用的时候直接从redis获取这两个常量，我就可以将这两个常量
     *     转为UUID。
     * </p>
     * @param uuid
     */
    public void onAppointUser(UUID uuid){
        SocketIOClient client = socketIOServer.getClient(uuid);
        client.sendEvent("UserSocket","这里是socketio发送的数据，只要客户端监听UserSocket这个事件，我这里就可以针对某个用户发送消息。");
    }
}
