# 一些有用的config
# /opt/bitnami/kafka/config/servers.properties
#```
#broker.id = 0 # this is unique id
#log.dirs = /path #log path, data and log segregation
#zookeeper.connect = zk1.2181, zk2.2181, zk3.2181
#```

# list topic
/opt/bitnami/kafka/bin/kafka-topics.sh --list --zookeeper 172.20.0.2:2181

# create topic
/opt/bitnami/kafka/bin/kafka-topics.sh --create --zookeeper 172.20.0.2:2181 --topic first --partitions 2 --replication-factor 1

# delete topic
/opt/bitnami/kafka/bin/kafka-topics.sh --delete --zookeeper 172.20.0.2:2181 --topic first

# Get detailed information
/opt/bitnami/kafka/bin/kafka-topics.sh --describe --zookeeper 172.20.0.2:2181 --topic first

# Start server
/opt/bitnami/kafka/bin/kafka-server-start.sh daemon /opt/bitnami/kafka/config/server.properties

# Stop server
/opt/bitnami/kafka/bin/kafka-server-stop.sh stop

# Create producer
/opt/bitnami/kafka/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic first

# Create consumer
/opt/bitnami/kafka/bin/kafka-console-consumer.sh --topic first --bootstrap-server localhost:9092
/opt/bitnami/kafka/bin/kafka-console-consumer.sh --topic first --bootstrap-server localhost:9092 --from-beginning
/opt/bitnami/kafka/bin/kafka-console-consumer.sh --topic first --bootstrap-server localhost:9092 --from-beginning --consumer.config=/opt/bitnami/kafka/config/consumer.properties


kafka-console-consumer.sh --topic __consumer_offsets --formatter "kafka.coordinator.group.GroupMetadataManager\$OffsetsMessageFormatter" --bootstrap-server localhost:9092 --consumer.config /opt/bitnami/kafka/config/consumer.properties --from-beginning