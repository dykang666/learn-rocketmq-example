package com.example.rocketmq.consumer.service2.Listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author kangdongyang
 * @version 1.0
 * @description:
 * @date 2024/6/21 14:43
 */
@Component
@Slf4j
@RocketMQMessageListener(consumeMode = ConsumeMode.ORDERLY,consumerGroup = "consumer-orderly",topic = "topic6",selectorExpression = "tag1")
public class MQConsumer6Orderly implements RocketMQListener<String> {

    @Override
    public void onMessage(String s) {
        log.info("接收到顺序消息了:{}",s);
    }
}
