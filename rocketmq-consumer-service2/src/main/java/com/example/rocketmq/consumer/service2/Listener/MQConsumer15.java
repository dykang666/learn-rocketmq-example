package com.example.rocketmq.consumer.service2.Listener;

import com.example.rocketmq.consumer.service2.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author kangdongyang
 * @version 1.0
 * @description:
 * @date 2024/6/20 19:43
 */
@Component
@RocketMQMessageListener(consumerGroup = "consumer-group-15",topic = "topic15",selectorExpression = "tag1")
@Slf4j
public class MQConsumer15 implements RocketMQListener<User> {

    @Override
    public void onMessage(User s) {
        log.info("consumer-15 收到string类型消息:{}",s);
    }
}
