start script:
vincent@ubuntu:/opt/module/kafka/bin$ ./kafka-server-start.sh ../config/server.properties &


create a topic:
vincent@ubuntu:/opt/module/kafka/bin$ ./kafka-topics.sh --create --zookeeper localhost:2182 --partitions 2 --replication-factor 1 --topic first

list all topics:
vincent@ubuntu:/opt/module/kafka/bin$ ./kafka-topics.sh --list --zookeeper localhost:2182

Get all the detail information for topic:
vincent@ubuntu:/opt/module/kafka/bin$ ./kafka-topics.sh --zookeeper localhost:2182 --describe topic first


Go inside a topic and send message: [localhost:9092 is ZooKeeper port]
vincent@ubuntu:/opt/module/kafka/bin$ ./kafka-console-producer.sh --broker-list localhost:9092 --topic first

Get messages from topic:
vincent@ubuntu:/opt/module/kafka/bin$ ./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic first --from-beginning

delete topic:
vincent@ubuntu:/opt/module/kafka/bin$ ./kafka-topics.sh --delete --bootstrap-server localhost:2182  --topic first

记住，Kafak should connected with Zookeeper, once Kafka connect with Zookeeper, will store you could go into ZooKeeper, exec the zkCLi.sh, and get the information from "ls /brokers" or "ls /consumers" command

相关知识：

1, One server is one broker
2, One Topic can cross different brokers, but identified by partition, for example, partition A, partition B.
3, You could replicate the same topic with parition content to another broker. For example, you could repliicate topA partition 0 to another broker, the same content, and identify as topic A, partition 0.
4, Obviously, the same topic with paritition, could be replicated count no more than brokers count.



5, You could create consumer, or consumer group to consume a message.
6, A messsage only be consumed one time in only one consumer in consumer group.
7, 需要详细说明的是，如果，有一个消费着群里面，有两个消费者，那么，他们只是不能同时消费同一个topic 的同一个分区而以，但是能够一个消费 topicA 的 partition 0, 一个能够消费 topic A 的 partition 1.但是，记住，一个消费者可以消费多个分区
8, 因此，最好是有多少个 partition, consumer group 就有多少个 consumer, 这样就可以并行处理了。

9, Kafka 写信息是写入 topic 的parition 中的，顺序写入。

面试题：
1,为什么要分区呢？
因为分区可以降低每个 broker 的负载（一个 broker 在同一个 topic 只有一个 parititon）, 因此增加并行。

2, 如何分区？
1, 如果有指定，那么就去指定的分区。
2, 如果有没有制定分区但是制定了key, 就hash算出
3, 如果没有指定分区和key, 只有value, 就轮训。

关于 replication (副本)
1, 同一个partition可能会有多个replication（对应 server.properties 配置中的 default.replication.factor=N）。
2, 没有replication的情况下，一旦broker 宕机，其上所有 patition 的数据都不可被消费，同时producer也不能再将数据存于其上的patition。
3, 引入replication之后，同一个partition可能会有多个replication，而这时需要在这些replication之间选出一个leader，producer和consumer只与这个leader交互，其它replication作为follower从leader 中复制数据。


进入 zooKeeper 能够获取相应的 broker 的 partition 信息
[zk: localhost:2181(CONNECTED) 3] get /brokers/topics/first/partitions/0/state


Consumer 读取 topic 的时候，是整个分区拉下来再下一个分区的，因此整体的顺序无法保证，但是分区内的顺序是保证的。


Please use org.apache.kafka.clients.consumer.KafkaConsumer instead

