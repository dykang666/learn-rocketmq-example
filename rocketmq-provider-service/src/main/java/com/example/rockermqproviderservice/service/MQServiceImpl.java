package com.example.rockermqproviderservice.service;

import com.example.rockermqproviderservice.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author kangdongyang
 * @version 1.0
 * @description:
 * @date 2024/6/20 17:23
 */
@Service
@Slf4j
public class MQServiceImpl implements MQService {

    @Autowired
    private RocketMQTemplate mqTemplate;
    /**
     * 同步消息
     * 消息发送方发出数据后，会在收到接收方发回响应之后才发下一个数据包的通讯方式
     */
    @Override
    public void syncMQMessageSend() {
//        mqTemplate.syncSend("topic1:tag1", "hello1");
//        mqTemplate.syncSend("topic1:tag1", "hello2");
//        SendResult s2=mqTemplate.syncSend("topic1:tag1", "hello3");
//        log.info("3条同步消息String类型已发送:topic:topic1,tag:tag1:{}",s2);
        User user=new User("tom",100);
        SendResult result=mqTemplate.syncSend("topic2:tag1", MessageBuilder.withPayload(user).build());
        //可以简写成以下，直接传入pojo对象
        SendResult result2=mqTemplate.syncSend("topic2:tag1", user.setName("lily").setAge(200));
        log.info("object类型同步消息发送结果:{},{}",result,result2);
    }

/**
 * 异步消息
 * 指发送方发出数据后，不等接收方发回响应，接着发送下个数据包
 * 关键实现异步发送回调接口（SendCallback）
 * 在执行消息的异步发送时应用不需要等待服务器响应即可直接返回，通过回调接口接收务器响应，并对服务器的响应结果进行处理
 * 这种方式任然需要返回发送消息任务的执行结果，异步不影响后续任务，不会造成阻塞
 */
    @Override
    public void asyncMQMessageSend() {
        User user=new User("tom",500);
        mqTemplate.asyncSend("topic3:tag1", user, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("异步消息发送成功:{}",sendResult);
            }

            @Override
            public void onException(Throwable throwable) {
                log.info("异步消息发送失败:{}",throwable.getMessage());
            }
        });
    }
/**
 * 单向消息
 * 特点为只负责发送消息，不等待服务器回应且没有回调函数触发，即只发送请求不等待应答
 * 此方式发送消息的过程耗时非常短，一般在微秒级别
 * 应用场景：适用于某些耗时非常短，但对可靠性要求并不高的场景，例如日志收集
 */
    @Override
    public void oneWaySendMQMessageSend() {
        User user=new User("tom",100);
        mqTemplate.sendOneWay("topic4:tag1", user);
        log.info("单向消息已发送");
    }
    /**
     * 延迟消息
     * rocketMQ的延迟消息发送其实是已发送就已经到broker端了，然后消费端会延迟收到消息。
     * RocketMQ 目前只支持固定精度的定时消息。
     * 固定等级：1到18分别对应1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
     * 延迟的底层方法是用定时任务实现的。
     */

    @Override
    public void delayedSendMQMessageSend() {
        User user=new User("tom",100);
        SendCallback sc=new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("发送异步延时消息成功");
            }

            @Override
            public void onException(Throwable throwable) {
                log.info("发送异步延时消息失败:{}",throwable.getMessage());
            }
        };
        Message<User> message=MessageBuilder.withPayload(user).build();
        //异步延时
        mqTemplate.asyncSend("topic5:tag1", message, sc, 3000, 3);
        //同步延时（少一个sendCallback）
        SendResult result=mqTemplate.syncSend("topic5:tag1", message, 3000, 3);
        log.info("发送同步延时消息成功:{}",result);
    }

    @Override
    public void selectorMQSend() {
//Tag过滤就是在发送参数上指定，比如topic1:tag1就指定了tag1，这种光topic1不指定就是所有tag
        //这里使用sql92
        User user=new User("tom",16);
        User user2=new User("klee",9);
        Message<User> message=MessageBuilder
                .withPayload(user)
                .setHeader("age", user.getAge())
                .build();
        Message<User> message2=MessageBuilder
                .withPayload(user2)
                .setHeader("age", user2.getAge())
                .build();
       // mqTemplate.syncSend("topic10", message);//age=16,消费者设置sql92过滤(header)头数据age=9
        mqTemplate.syncSend("topic10", message2);//age=9
        log.info("添加age头信息的过滤消息发送完毕");

    }
    /**
     * 顺序消息
     *使用hashcode对topic队列数量取模得到对应队列
     * 使消息按照顺序被消费，顺序与生产出来的顺序一致
     * 比如同一个订单生成，付费顺序需要一致，可以按照订单id来当作hashkey
     */
    @Override
    public void orderlyMQMessageSend() {
        String s1[]={"tom","1"};
        String s2[]={"klee我和tom在同一个消费者被消费，而且在tom之后","1"};
        String s3[]={"lily我可能不会和tom在同一个消费者被消费","2"};
        //同步顺序，也可以是其他类型比如异步顺序，单向顺序
        mqTemplate.syncSendOrderly("topic6:tag1", s1[0],s1[1]);
        mqTemplate.syncSendOrderly("topic6:tag1", s2[0],s2[1]);
        //mqTemplate.syncSendOrderly("topic6:tag1", s3[0],s3[1]);
        log.info("顺序消息发送完毕");
    }
    /**
     * 分布式事物消息
     *生产者需要一个监听自己的类
     */
    @Override
    public void transactionMQMessageSend() {
        User user=new User("klee",9);
        Message<User> message=MessageBuilder
                .withPayload(user)
                .setHeader(RocketMQHeaders.TRANSACTION_ID, UUID.randomUUID())
                .build();
        TransactionSendResult result=mqTemplate.sendMessageInTransaction("topic15:tag1", message, null);
        if(result.getLocalTransactionState().equals(LocalTransactionState.COMMIT_MESSAGE)
                &&
                result.getSendStatus().equals(SendStatus.SEND_OK)){
            log.info("事物消息发送成功");

        }
        log.info("事物消息发送结果:{}",result);
    }

    /**
     * 分区顺序消息
     * 分区顺序消息是指消息发送后，按照指定的顺序被消费者消费。
     * 分区顺序消息适用于需要保证消息的顺序性场景，比如日志收集、消息通知等。
     * 分区顺序消息通常用于以下场景：
     */
    @Override
    public void partOrderlyMQMessageSend() {
        // 模拟多个订单及其操作（订单创建、支付、完成）
        String[] tags = {"Create", "Pay", "Complete"};
        // 模拟多个订单，每个订单有三个操作
        for (int orderId = 1; orderId <= 3; orderId++) {
            for (String tag : tags) {
                // 发送消息，指定队列选择器
                String message = String.format("OrderID: %d, Step: %s", orderId, tag);
                // 使用 syncSendOrderly 方法发送顺序消息
                try {
                    mqTemplate.syncSendOrderly("OrderTopic:tag1", message, String.valueOf(orderId));
                } catch (Exception e) {
                    log.info("Failed to send message to MQ: " + e.getMessage());

                }
            }
        }
    }

    /**
     * 发送模拟消息  在消费端模拟死信队列
     */
    @Override
    public void sendSimulateMessage() {
        // 发送消息
        for (int i = 0; i < 1; i++) {
            String message = "Test message " + i;
            try {
                mqTemplate.syncSend("topic17:tag1",message);
            } catch (Exception e) {
                log.info("Failed to send message to MQ: " + e.getMessage());
            }
        }

    }
}
