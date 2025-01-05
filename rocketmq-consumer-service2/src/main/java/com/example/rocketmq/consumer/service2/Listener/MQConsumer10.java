package com.example.rocketmq.consumer.service2.Listener;

import com.example.rocketmq.consumer.service2.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.stereotype.Component;

/**
 * @author kangdongyang
 * @version 1.0
 * @description:
 * 实际使用中，要保证消费订阅关系一致，一个消费者组中的订阅Topic和Tag必须一致。
 * @date 2024/6/20 19:38
 */
@Component
@RocketMQMessageListener(consumerGroup = "consumer-group-10",topic = "",
        selectorExpression = "age = 9",selectorType = SelectorType.SQL92)
@Slf4j
public class MQConsumer10 implements RocketMQListener<User>, RocketMQPushConsumerLifecycleListener {

    @Override
    public void onMessage(User user) {
        log.info("consumer-group-10 收到user类型消息:{}",user);
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        defaultMQPushConsumer.setConsumeThreadMax(30);
        defaultMQPushConsumer.setConsumeThreadMin(25);
        //订阅多个topic
        try {
            defaultMQPushConsumer.subscribe("topic10","*");
            defaultMQPushConsumer.subscribe("topic11","*");
        } catch (MQClientException e) {
            throw new RuntimeException(e);
        }
       // defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);

    }
}
