package com.atguigu.kafka;

import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class LowerAPI {

    public static void main(String[] args) {
        List<String> lists = new ArrayList<>();
        lists.add("localhost");

        int port = 9092;

        String topic = "first";

        int partition = 1;

        long offset = 2;

        new LowerAPI().findLeader(lists, port, topic, partition);
    }

    private String findLeader(List<String> brokers, int port, String topic, int partition) {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "localhost:9092");
        props.setProperty("group.id", "test");
        props.setProperty("enable.auto.commit", "true");
        props.setProperty("auto.commit.interval.ms", "1000");
        props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);




return null;
    }

    private void getData() {

    }

}
