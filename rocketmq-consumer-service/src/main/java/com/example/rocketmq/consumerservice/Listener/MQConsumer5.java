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
 * 订单超时未支付，取消订单并释放库存
 *  1、可以通过消息队列
 *  2、也可以通过定时任务
 * @date 2024/6/20 19:38
 */
@Component
@RocketMQMessageListener(consumerGroup = "consumer-group-5",topic = "topic5",selectorExpression = "tag1")
public class MQConsumer5 implements RocketMQListener<User> {
    Logger log= LoggerFactory.getLogger(MQConsumer5.class);

    @Override
    public void onMessage(User user) {
        //
        long  orderId=9999;
        // 模拟检查订单是否超时未支付
        boolean isOrderPaid = checkOrderPaymentStatus(orderId);  // 你可以根据业务逻辑实现该方法
        if (!isOrderPaid) {
            cancelOrder(orderId);  // 取消订单
            releaseStock(orderId);  // 释放库存
        }
        //不做什么处理
        log.info("consumer-group-5 收到user类型消息:{}",user);
    }

    private static boolean checkOrderPaymentStatus(long orderId) {
        // 这里可以根据订单ID查询数据库，判断该订单是否支付
        return false;  // 假设订单未支付
    }


    private static void cancelOrder(long orderId) {
        // 取消订单逻辑
        System.out.println("取消订单: " + orderId);
    }

    private static void releaseStock(long orderId) {
        // 释放库存逻辑
        System.out.println("释放库存: " + orderId);
    }
}
