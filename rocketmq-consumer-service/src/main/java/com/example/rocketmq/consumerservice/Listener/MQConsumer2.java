package com.example.rocketmq.consumerservice.Listener;


import com.example.rocketmq.consumerservice.entity.User;
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
@RocketMQMessageListener(consumerGroup = "consumer-group-2",topic = "topic2",selectorExpression = "tag1")
public class MQConsumer2 implements RocketMQListener<User> {
    Logger log= LoggerFactory.getLogger(MQConsumer2.class);

    @Override
    public void onMessage(User user) {
        log.info("consumer-2 收到user类型消息:{}",user);
    }
}
