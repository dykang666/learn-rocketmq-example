package com.example.rockermqproviderservice.service;

/**
 * @author kangdongyang
 * @version 1.0
 * @description:
 * @date 2024/6/20 17:18
 */
public interface MQService {
    /**
     * 同步消息
     * 消息发送方发出数据后，会在收到接收方发回响应之后才发下一个数据包的通讯方式
     */
    void syncMQMessageSend();

/**
 * 异步消息
 * 指发送方发出数据后，不等接收方发回响应，接着发送下个数据包
 * 关键实现异步发送回调接口（SendCallback）
 * 在执行消息的异步发送时应用不需要等待服务器响应即可直接返回，通过回调接口接收务器响应，并对服务器的响应结果进行处理
 * 这种方式任然需要返回发送消息任务的执行结果，异步不影响后续任务，不会造成阻塞
 */
    void asyncMQMessageSend();
    /**
     * 单向消息
     * 特点为只负责发送消息，不等待服务器回应且没有回调函数触发，即只发送请求不等待应答
     * 此方式发送消息的过程耗时非常短，一般在微秒级别
     * 应用场景：适用于某些耗时非常短，但对可靠性要求并不高的场景，例如日志收集
     */
    void oneWaySendMQMessageSend();

    /**
     * 延迟消息
     * rocketMQ的延迟消息发送其实是已发送就已经到broker端了，然后消费端会延迟收到消息。
     * RocketMQ 目前只支持固定精度的定时消息。
     * 固定等级：1到18分别对应1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
     * 延迟的底层方法是用定时任务实现的。
     */
    void delayedSendMQMessageSend();
    /**
     * 顺序消息
     *使用hashcode对topic队列数量取模得到对应队列
     * 使消息按照顺序被消费，顺序与生产出来的顺序一致
     * 比如同一个订单生成，付费顺序需要一致，可以按照订单id来当作hashkey
     */
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
    void selectorMQSend();

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
    void orderlyMQMessageSend();


    /**
     * 分布式事物消息
     *生产者需要一个监听自己的类
     */
    void transactionMQMessageSend();


    void partOrderlyMQMessageSend();



    void sendSimulateMessage();
}
