package com.atguigu.kafka;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class CustomProducer {
	public static void main(String[] args) {
		Properties props = new Properties();
		// Kafka服务端的主机名和端口号
		props.put("bootstrap.servers", "localhost:9092");
		// 等待所有副本节点的应答
		props.put(ProducerConfig.ACKS_CONFIG, "all");


		//Interceptor
		List<String> interceptorClasses = new ArrayList<>();
		interceptorClasses.add("com.atguigu.kafka.interceptor.TimeInterceptor");
		interceptorClasses.add("com.atguigu.kafka.interceptor.CountInterceptor");
		props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, interceptorClasses);


		// 消息发送最大尝试次数
		props.put("retries", 0);
		// 一批消息处理大小
		props.put("batch.size", 16384);
		// 请求延时
		props.put("linger.ms", 1);
		// 发送缓存区内存大小
		props.put("buffer.memory", 33554432);
		// key序列化
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		// value序列化
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		/**
		 * 强制全部使用1号分区
		 */
		props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "com.atguigu.kafka.CustomPartitioner");

		KafkaProducer<String, String> producer = new KafkaProducer<>(props);
		for (int i = 0; i < 2; i++) {
			producer.send(new ProducerRecord<>("first", Integer.toString(i), "hello world-" + i), (metadata, exception) -> {
				System.out.println("=======================================================");
				System.out.println("offset:" + metadata.offset());
				System.out.println("partition:" + metadata.partition());
				System.out.println("toString:" + metadata.toString());
				System.out.println("hasOffset:" + metadata.hasOffset());
				System.out.println("hasTimestamp:" + metadata.hasTimestamp());
				System.out.println("serializedKeySize:" + metadata.serializedKeySize());
				System.out.println("serializedValueSize:" + metadata.serializedValueSize());
				System.out.println("topic:" + metadata.topic());
				System.out.println("=======================================================");
			});
		}

		producer.close();
	}
}

