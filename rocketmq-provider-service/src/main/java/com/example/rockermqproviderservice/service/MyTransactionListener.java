package com.example.rockermqproviderservice.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;

/**
 * @author kangdongyang
 * @version 1.0
 * @description:
 * // RocketMQTemplate 只能设置一个事务监听器，
 * 1、如果要使用多个监听器，需要使用多个RocketMQTemplate。
 * 2、在 TransactionListener 中根据消息内容进行判断：
 * @date 2024/6/21 14:03
 */
@Slf4j
@RocketMQTransactionListener
public class MyTransactionListener implements TransactionListener {

    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object o) {
        // 根据消息内容判断事务状态
        if (msg.getTopic().equals("OrderTopic")) {
            // 检查订单事务状态  处理订单同的逻辑
            // 处理本地事务  幂等性
            return LocalTransactionState.COMMIT_MESSAGE;
        } else if (msg.getTopic().equals("AnotherTopic")) {
            // 检查另一种事务状态   处理订单同的逻辑
            // 处理本地事务  幂等性
            return LocalTransactionState.ROLLBACK_MESSAGE;
        } else {
            // 未知的消息，不做处理
            return LocalTransactionState.UNKNOW;
        }
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        // 根据消息内容判断事务状态
        if (messageExt.getTopic().equals("OrderTopic")) {
            // 检查订单事务状态   处理订单同的逻辑
            return LocalTransactionState.COMMIT_MESSAGE;
        } else if (messageExt.getTopic().equals("AnotherTopic")) {
            // 检查另一种事务状态   处理订单同的逻辑
            return LocalTransactionState.ROLLBACK_MESSAGE;
        } else {
            // 未知的消息，不做处理
            return LocalTransactionState.UNKNOW;
        }
    }
}
