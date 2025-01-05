package com.example.rockermqproviderservice.controller;

import com.example.rockermqproviderservice.service.MQService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kangdongyang
 * @version 1.0
 * @description:
 * @date 2024/6/20 19:56
 */
@RestController
@RequestMapping("/api/mq")
@Slf4j
public class MqTestController {

    @Autowired
    private MQService mqService;


    /**
     * 触发同步发送MQ消息的接口
     *
     * @return 成功时返回确认信息，失败则抛出异常
     */
    @PostMapping("/send")
    public String sendMessageToMQ() {
        try {
            mqService.syncMQMessageSend(); // 调用服务层方法发送消息
            return "Message sent to MQ successfully!";
        } catch (Exception e) {
            // 处理异常，可根据需要自定义异常处理逻辑
            return "Failed to send message to MQ: " + e.getMessage();
        }
    }
    /**
     * 触发异步发送MQ消息的接口
     *
     * @return 成功时返回确认信息，失败则抛出异常
     */

    @PostMapping("/async")
    public String asyncSendMessageToMQ() {
        try {
            mqService.asyncMQMessageSend(); // 调用服务层方法发送消息
            return "Message sent to MQ successfully!";
        } catch (Exception e) {
            // 处理异常，可根据需要自定义异常处理逻辑
            return "Failed to send message to MQ: " + e.getMessage();
        }
    }

    /**
     * 单向消息
     * 单向消息指的是只负责发送消息，而不等待服务器回应且没有回调函数触发，即只发送请求不等待应答。
     * 适用于某些耗时非常短，但对可靠性要求并不高的场景，例如日志收集。
     * 单向消息通常用于以下场景：
     * 1. 日志收集：单向消息可用于收集系统的操作日志，将日志发送到 MQ 中，由日志系统进行处理。
     * 2. 监控指标：单向消息可用于收集系统的监控指标，将指标发送到 MQ 中，由监控系统进行处理。
     * 3. 消息发送：单向消息可用于发送消息，比如发送邮件、短信等。
     **/
    @PostMapping("/oneWay")
    public String oneWaySendMessageToMQ() {
        try {
            mqService.oneWaySendMQMessageSend(); // 调用服务层方法发送消息
            return "Message sent to MQ successfully!";
        } catch (Exception e) {
            // 处理异常，可根据需要自定义异常处理逻辑
            return "Failed to send message to MQ: " + e.getMessage();
        }
    }

    /**
     * 延迟消息
     * 延迟消息是指消息发送后，并不立即被消费者消费，而是在指定时间后才被消费。
     * 延迟消息适用于需要在未来某个时间点执行任务的场景，比如定时任务、定时通知等。
     * 延迟消息通常用于以下场景：
     * 1. 定时任务：延迟消息可用于实现定时任务，比如在某个时间点执行某个任务，而不需要实时触发。
     * 2. 定时通知：延迟消息可用于实现定时通知，比如在某个时间点发送邮件、短信通知用户。
     * 3. 消息重试：延迟消息可用于实现消息重试机制，比如在消息发送失败后，延迟一段时间后重试发送。
     * 4. 消息过期：延迟消息可用于实现消息过期机制，比如在消息发送后，延迟一段时间后自动删除消息。
     * 5. 消息调度：延迟消息可用于实现消息调度机制，比如在某个时间点执行某个任务
     **/

    @PostMapping("/delayed")
    public String delayedSendMessageToMQ() {
        try {
            mqService.delayedSendMQMessageSend(); // 调用服务层方法发送消息
            return "Message sent to MQ successfully!";
        } catch (Exception e) {
            // 处理异常，可根据需要自定义异常处理逻辑
            return "Failed to send message to MQ: " + e.getMessage();
        }
    }
    /**
     * 过滤消息
     * Tag 过滤
     * Sql 过滤
     * Sql类型语法：
     * 数值比较，比如：>，>=，<，<=，BETWEEN，=；
     * 字符比较，比如：=，<>，IN；
     * IS NULL 或者 IS NOT NULL；
     * 逻辑符号 AND，OR，NOT；
     */
    @PostMapping("/selector")
    public String selectorSendMessageToMQ() {
        try {
            mqService.selectorMQSend(); // 调用服务层方法发送消息
            return "Message sent to MQ successfully!";
        } catch (Exception e) {
            // 处理异常，可根据需要自定义异常处理逻辑
            return "Failed to send message to MQ: " + e.getMessage();
        }
    }

    /**
     * 分布式事物消息
     *生产者需要一个监听自己的类
     */
    @PostMapping("/transaction")
    public String transactionSendMessageToMQ() {
        try {
            mqService.transactionMQMessageSend(); // 调用服务层方法发送消息
            return "Message sent to MQ successfully!";
        } catch (Exception e) {
            // 处理异常，可根据需要自定义异常处理逻辑
            return "Failed to send message to MQ: " + e.getMessage();
        }
    }

    /**
     * 顺序消息  局部顺序
     * 顺序消息是指消息发送后，按照指定的顺序被消费者消费。
     * 顺序消息适用于需要保证消息的顺序性场景，比如日志收集、消息通知等。
     * 顺序消息通常用于以下场景：
     * 1. 日志收集：顺序消息可用于收集系统的操作日志，按照时间顺序将日志发送到 MQ 中，由日志系统进行处理。
     * 2. 消息通知：顺序消息可用于发送消息，比如发送邮件、短信等，按照时间顺序将消息发送到 MQ 中，由消息系统进行处理。
     * 3. 消息调度：顺序消息可用于实现消息调度机制，比如在某个时间点执行某个任务，按照时间顺序将任务发送到 MQ 中，由任务系统进行处理。
     * 4. 消息队列：顺序消息可用于实现消息队列机制，比如在某个时间点执行某个任务，按照时间顺序将任务发送到 MQ 中，由任务系统进行处理。
     * */

    @PostMapping("/orderly")
    public String orderlySendMessageToMQ() {
        try {
            mqService.orderlyMQMessageSend(); // 调用服务层方法发送消息
            return "Message sent to MQ successfully!";
        } catch (Exception e) {
            // 处理异常，可根据需要自定义异常处理逻辑
            return "Failed to send message to MQ: " + e.getMessage();
        }
    }

    @PostMapping("/partOrderlyMQMessageSend")
    public String partOrderlyMQMessageSend() {
        // 调用服务层方法发送消息
        try {
            mqService.partOrderlyMQMessageSend();
            return "Message sent to MQ successfully!";
        } catch (Exception e) {
            // 处理异常，可根据需要自定义异常处理逻辑
            return "Failed to send message to MQ: " + e.getMessage();
        }
    }
    @PostMapping("/sendSimulateMessage")
    public String sendSimulateMessage() {
        // 调用服务层方法发送消息
        try {
            mqService.sendSimulateMessage();
            return "Message sent to MQ successfully!";
        } catch (Exception e) {
            // 处理异常，可根据需要自定义异常处理逻辑
            return "Failed to send message to MQ: " + e.getMessage();
        }
    }


}

