package com.example.rocketmq.consumerservice.Listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author kangdongyang
 * @version 1.0
 * @description:
 * 为了快速测试 修要 messageDelayLevel = "1s"
 * 和设置最大重试次数
 * @date 2025/1/4 21:56
 */
@Component
@RocketMQMessageListener(consumerGroup = "consumer-group-Dead-Letter-Queue",topic = "%DLQ%consumer-group-17",selectorExpression = "tag1")
@Slf4j
public class DeadLetterQueueConsumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String s) {
        log.info("DeadLetterQueueConsumer 收到string类型消息:{}",s);
    }
}
