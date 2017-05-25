package com.hai.web.service.kafka;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.Arrays;
import java.util.Properties;

/**
 * Created by cloud on 2017/3/27.
 */
public class KfkConfig {

    private String topic;
    private String kfkServers;
    private int threadn;
    private int acks = 1;
    private int retries = 0;
    private int batchSize = 16384;
    private String groupId;
    private String autoCommit="true";
    private String deserializer = "org.apache.kafka.common.serialization.StringDeserializer";
    private String serializer = "org.apache.kafka.common.serialization.StringDeserializer";
    private KafkaConsumer<String,String> kc;
    private KafkaProducer<String,String> kp;

    public KafkaConsumer<String,String> getConsumer(){
        if(kc == null){
            Properties props = new Properties();
            props.put("bootstrap.servers", getKfkServers());
            props.put("group.id",getGroupId());
            props.put("enable.auto.commit", getAutoCommit());
            props.put("auto.commit.interval.ms", "1000");
            props.put("session.timeout.ms", "30000");
            props.put("key.deserializer", getDeserializer());
            props.put("value.deserializer", getDeserializer());
            kc = new KafkaConsumer<String, String>(props);
            kc.subscribe(Arrays.asList(getTopic().split(",")));
        }
        return kc;
    }

    public KafkaProducer<String,String> getProducer(){
        if(kp == null){
            Properties props = new Properties();
            props.put("acks", getAcks());
            props.put("retries", getRetries());
            props.put("batch.size", getBatchSize());
            props.put("bootstrap.servers", getKfkServers());
            props.put("key.serializer", getSerializer());
            props.put("value.serializer", getSerializer());
            kp = new KafkaProducer<String, String>(props);
        }
        return kp;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getKfkServers() {
        return kfkServers;
    }

    public void setKfkServers(String kfkServers) {
        this.kfkServers = kfkServers;
    }

    public int getThreadn() {
        return threadn;
    }

    public void setThreadn(int threadn) {
        this.threadn = threadn;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getAutoCommit() {
        return autoCommit;
    }

    public void setAutoCommit(String autoCommit) {
        this.autoCommit = autoCommit;
    }

    public String getDeserializer() {
        return deserializer;
    }

    public void setDeserializer(String deserializer) {
        this.deserializer = deserializer;
    }

    public String getSerializer() {
        return serializer;
    }

    public void setSerializer(String serializer) {
        this.serializer = serializer;
    }

    public int getAcks() {
        return acks;
    }

    public void setAcks(int acks) {
        this.acks = acks;
    }

    public int getRetries() {
        return retries;
    }

    public void setRetries(int retries) {
        this.retries = retries;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }
}
