package com.example.rocketmq.consumerservice.Listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author kangdongyang
 * @version 1.0
 * @description:
 * 消费死信队列    DeadLetterQueueConsumer
 * @date 2025/1/4 21:51
 */
@Component
@RocketMQMessageListener(consumerGroup = "consumer-group-17",topic = "topic17",selectorExpression = "tag1")
@Slf4j
public class MqConsumer17 implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        System.out.println("receive retry message:" + message);
        //此处抛出一个 RuntimeException 异常，模拟消费失败
        throw new RuntimeException("故意抛出异常用于消息重试");
    }
}
