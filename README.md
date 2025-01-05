1、 RocketMQ 各种消息 同步消息、异步消息、单向消息、延迟消息、过滤消息、事务消息、顺序消息（局部顺序）、死信消息，详见MqTestController。

2、运行此代码需要安装RocketMq环境

3、RocketMQ事务消息属于最终一致性。

seata和RocketMQ的消息对比
![image](https://github.com/user-attachments/assets/fc17deea-0d88-4979-a94a-97a5a046f0db)

本地事务修改不支持全局性事务，只支持单个服务切换数据源处理
4、![image](https://github.com/user-attachments/assets/d731a689-fef6-4673-abda-8a83605a2ec6)

5、 实际项目中，Seata和RocketMQ分布式事务共同使用，根据不同的场景使用seata分布式事务或者RocketMQ分布式事务。



