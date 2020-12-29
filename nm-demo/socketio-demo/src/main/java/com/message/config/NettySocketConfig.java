package com.message.config;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * socketIo 配置类
 * <p>在这里可以配置socketIo的相关信息，包括连接路径和端口等。但是这些信息都设置在配置文件中配置了，具体的解释在配置yml文件中有讲解。
 * <b>注意：如果需要给socketIo设置权限，可以在配置文件中设置，例如下面这个案例。</b>
 * config.setAuthorizationListener(new AuthorizationListener() {//类似过滤器
 *             @Override
 *             public boolean isAuthorized(HandshakeData data) {
 *                 //http://localhost:8081?username=test&password=test
 *                 //例如果使用上面的链接进行connect，可以使用如下代码获取用户密码信息，本文不做身份验证
 *                 // String username = data.getSingleUrlParam("username");
 *                 // String password = data.getSingleUrlParam("password");
 *                 return true;
 *             }
 *         });
 *
 * </p>
 */
@Configuration
public class NettySocketConfig {

    @Value("${socketIo.host}")
    private String host;

    @Value("${socketIo.port}")
    private int port;

    @Value("${socketIo.maxFramePayloadLength}")
    private int maxFramePayloadLength;

    @Value("${socketIo.maxHttpContentLength}")
    private int maxHttpContentLength;

    @Value("${socketIo.bossCount}")
    private int bossCount;

    @Value("${socketIo.workCount}")
    private int workCount;

    @Value("${socketIo.allowCustomRequests}")
    private boolean allowCustomRequests;

    @Value("${socketIo.upgradeTimeout}")
    private int upgradeTimeout;

    @Value("${socketIo.pingTimeout}")
    private int pingTimeout;

    @Value("${socketIo.pingInterval}")
    private int pingInterval;

    /**
     * 将相关配置注入到容器
     * @return
     * @throws Exception
     */
    @Bean
    public SocketIOServer socketIOServer() throws Exception {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname(host);
        config.setPort(port);
        config.setMaxFramePayloadLength(maxFramePayloadLength);
        config.setMaxHttpContentLength(maxHttpContentLength);
        config.setBossThreads(bossCount);
        config.setWorkerThreads(workCount);
        config.setAllowCustomRequests(allowCustomRequests);
        config.setUpgradeTimeout(upgradeTimeout);
        config.setPingTimeout(pingTimeout);
        config.setPingInterval(pingInterval);

        //该处进行身份验证
        config.setAuthorizationListener(handshakeData -> {
            return true;
        });
        final SocketIOServer server = new SocketIOServer(config);
        return server;
    }

    /**
     * 开启注解
     * @param socketServer
     * @return
     */
    @Bean
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {
        return new SpringAnnotationScanner(socketServer);
    }
}

