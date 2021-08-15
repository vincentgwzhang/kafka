package com.atguigu.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.*;

public class CallBackProducer {

	public static void main(String[] args) throws InterruptedException {
		Properties props = new Properties();
		// Kafka服务端的主机名和端口号
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
		// 等待所有副本节点的应答
		props.put(ProducerConfig.ACKS_CONFIG, "1");
		// 消息发送最大尝试次数
		props.put("retries", 0);
		// 一批消息处理大小
		props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
		// 增加服务端请求延时
		props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
		// 发送缓存区内存大小
		props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
		// key序列化
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
		// value序列化
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
		// 自定义分区
		props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "com.atguigu.kafka.CustomPartitioner");

		KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(props);

		for (int i = 0; i < 3; i++) {
			Thread.sleep(500);
			kafkaProducer.send(new ProducerRecord<>("first", "hh" + i), (metadata, exception) -> {

				System.out.println("partition:" + metadata.partition() + " --- offset:" + metadata.offset());

				if (exception != null) {
					exception.fillInStackTrace();
				}
			});
		}

		kafkaProducer.close();

	}

}
