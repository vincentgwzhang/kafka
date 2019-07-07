package com.atguigu.kafka.streams;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.Topology;

import java.util.Properties;


/**
 * 这个类的作用就是起到桥梁的作用，拦截sender 的topic, 提取内容，作自己的业务以后，就发到下一个 topic
 * 在这个例子里面，是首先拦截叫 “first” 的 topic, 然后发送到 "second" 那里
 */
public class StreamDemo {

    public static void main(String[] args) {

        String fromTopic = "first";
        String toTopic   = "second";

        String sourceName    = "SOURCE";
        String processorName = "PROCESSOR";

        Topology topology = new Topology();
        topology.addSource(sourceName, fromTopic);
        topology.addProcessor(processorName, () -> new LogProcessor(), sourceName);
        topology.addSink("SINK", toTopic, processorName);


        Properties properties = new Properties();
        properties.put("application.id","kafka_stream");
        properties.put("bootstrap.servers","localhost:9092");

        KafkaStreams kafkaStreams = new KafkaStreams(topology, properties);
        kafkaStreams.start();


    }

}
