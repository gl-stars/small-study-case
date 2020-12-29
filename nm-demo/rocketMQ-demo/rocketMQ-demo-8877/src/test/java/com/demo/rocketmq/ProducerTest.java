package com.demo.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 这时发送同步消息
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProducerTest {

    /***
     * 发送同步消息
     * @throws Exception
     */
    @Test
    public void SyncProducer() throws Exception {
        // 实例化消息生产者Producer
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        // 设置NameServer的地址
        producer.setNamesrvAddr("192.168.88.129:9876");
        // 启动Producer实例
        producer.start();
        for (int i = 0; i < 100; i++) {
            /***
             * 创建消息，并指定Topic，Tag和消息体 body 发送的消息数据。这里的消息数据需要 byte 数组类型，所以我们需要转为 byte 类型数组，
             * RemotingHelper.DEFAULT_CHARSET 设置为UTF-8编码
             */
            Message msg = new Message("TopicTest","TagA","这是发送到消息队列的数据".getBytes(RemotingHelper.DEFAULT_CHARSET));
            // 发送消息到一个Broker
            SendResult sendResult = producer.send(msg);
            // 通过sendResult返回消息是否成功送达
            System.out.println(sendResult);
            // 是否送达的状态
            System.out.println(sendResult.getSendStatus());
        }
        // 如果不再发送消息，关闭Producer实例。
        producer.shutdown();
    }


    @Test
    public void AsyncProducer() throws Exception{
        // 实例化消息生产者Producer
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        // 设置NameServer的地址
        producer.setNamesrvAddr("192.168.88.129:9876");
        // 启动Producer实例
        producer.start();
        // 重试数目发送失败重试的数目，默认为 2
        producer.setRetryTimesWhenSendAsyncFailed(0);
        for (int i = 0; i < 100; i++) {
            /***
             * 创建消息，并指定Topic，Tag和消息体 body 发送的消息数据。这里的消息数据需要 byte 数组类型，所以我们需要转为 byte 类型数组，
             * RemotingHelper.DEFAULT_CHARSET 设置为UTF-8编码
             */
            Message msg = new Message("TopicTest","TagA","这时发送到消息队列中的异步消息".getBytes(RemotingHelper.DEFAULT_CHARSET));
            // SendCallback接收异步返回结果的回调
            producer.send(msg, new SendCallback() {
                /***
                 * 发送成功回调方法
                 * @param sendResult
                 */
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println(sendResult);
                    System.out.printf(sendResult.getMsgId());
                }

                /***
                 * 发送失败回调方法
                 * @param e
                 */
                @Override
                public void onException(Throwable e) {
                    System.out.println("发送失败");
                    e.printStackTrace();
                }
            });
        }
        // 如果不再发送消息，关闭Producer实例。
        producer.shutdown();
    }

    /**
     * 发送单向消息
     */
    @Test
    public void OnewayProducer() throws Exception {
    // 实例化消息生产者Producer
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        // 设置NameServer的地址
        producer.setNamesrvAddr("192.168.88.129:9876");
        // 启动Producer实例
        producer.start();
        for (int i = 0; i < 100; i++) {
            /***
             * 创建消息，并指定Topic，Tag和消息体 body 发送的消息数据。这里的消息数据需要 byte 数组类型，所以我们需要转为 byte 类型数组，
             * RemotingHelper.DEFAULT_CHARSET 设置为UTF-8编码
             */
            Message msg = new Message("TopicTest", "TagA", "这时单向消息".getBytes(RemotingHelper.DEFAULT_CHARSET));
            // 发送单向消息，没有任何返回结果
            producer.sendOneway(msg);

        }
        // 如果不再发送消息，关闭Producer实例。
        producer.shutdown();
    }

    /*********************  消费消息   *********************/

    /***
     * 负载均衡消费
     */
    @Test
    public void consumeLoad() throws Exception{
        // 实例化消息生产者,指定组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
        // 指定Namesrv地址信息.
        consumer.setNamesrvAddr("192.168.88.129:9876");
        // 订阅Topic
        consumer.subscribe("TopicTest", "*");
        //负载均衡模式消费
        consumer.setMessageModel(MessageModel.CLUSTERING);
        // 注册回调函数，处理消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
                System.out.printf("%s Receive New Messages: %s %n",
                        Thread.currentThread().getName(), msgs);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        //启动消息者
        consumer.start();
        System.out.printf("Consumer Started.%n");
    }

    /***
     * 广播式消费
     */
    @Test
    public void consumeWhole() throws Exception{
        // 实例化消息生产者,指定组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
        // 指定Namesrv地址信息.
        consumer.setNamesrvAddr("192.168.88.129:9876");
        // 订阅Topic
        consumer.subscribe("TopicTest", "*");
        //广播模式消费
        consumer.setMessageModel(MessageModel.BROADCASTING);
        // 注册回调函数，处理消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
                System.out.printf("%s Receive New Messages: %s %n",
                        Thread.currentThread().getName(), msgs);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        //启动消息者
        consumer.start();
        System.out.printf("Consumer Started.%n");
    }
}