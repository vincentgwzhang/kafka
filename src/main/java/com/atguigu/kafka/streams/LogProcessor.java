package com.atguigu.kafka.streams;

import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;

public class LogProcessor implements Processor<byte[], byte[]> {

    private ProcessorContext context;

    @Override
    public void init(ProcessorContext context) {
        this.context = context;
    }

    @Override
    public void process(byte[] key, byte[] value) {
        String line = new String(value);
        line = line.replaceAll(">>>", "");
        value = line.getBytes();
        context.forward(key, value);
    }

    @Override
    public void close() {

    }
}
