package com.example.rocketmq.consumerservice.Listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author kangdongyang
 * @version 1.0
 * @description:
 * @date 2025/1/4 17:59
 */
@Slf4j
@Component
@RocketMQMessageListener(consumeMode = ConsumeMode.ORDERLY,consumerGroup = "consumer-16",topic = "OrderTopic",selectorExpression = "tag1")
public class MQOrderConsumer16  implements RocketMQListener<String> {
    @Override
    public void onMessage(String s) {
        log.info("MQOrderConsumer16 收到string类型消息:{}",s);
    }
}
