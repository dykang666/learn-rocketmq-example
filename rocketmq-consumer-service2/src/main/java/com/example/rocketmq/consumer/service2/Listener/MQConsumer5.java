package com.example.rocketmq.consumer.service2.Listener;

import com.example.rocketmq.consumer.service2.entity.User;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author kangdongyang
 * @version 1.0
 * @description:
 * @date 2024/6/20 19:38
 */
@Component
@RocketMQMessageListener(consumerGroup = "consumer-group-5",topic = "topic5",selectorExpression = "tag1")
public class MQConsumer5 implements RocketMQListener<User> {
    Logger log= LoggerFactory.getLogger(MQConsumer5.class);

    @Override
    public void onMessage(User user) {
        log.info("consumer-group-5 收到user类型消息:{}",user);
    }
}
