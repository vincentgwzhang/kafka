package com.atguigu.kafka;

import java.util.Map;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

public class CustomPartitioner implements Partitioner {

	@Override
	public void configure(Map<String, ?> configs) {
		
	}

	@Override
	public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
		Integer integer = cluster.partitionCountForTopic(topic);
		return keyBytes.hashCode() % integer;
	}

	@Override
	public void close() {
		
	}
}


