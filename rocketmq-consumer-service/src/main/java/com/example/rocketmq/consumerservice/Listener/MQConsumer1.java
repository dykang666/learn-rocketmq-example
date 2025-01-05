package com.example.rocketmq.consumerservice.Listener;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author kangdongyang
 * @version 1.0
 * @description:
 * @date 2024/6/20 19:43
 */
@Component
@RocketMQMessageListener(consumerGroup = "consumer-group-1",topic = "topic1",selectorExpression = "tag1")
public class MQConsumer1  implements RocketMQListener<String> {

    Logger log= LoggerFactory.getLogger(MQConsumer1.class);

    @Override
    public void onMessage(String s) {
        log.info("consumer-1 收到string类型消息:{}",s);
    }
}
