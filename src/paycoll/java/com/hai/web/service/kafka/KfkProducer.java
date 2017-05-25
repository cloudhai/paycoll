package com.hai.web.service.kafka;

import com.hai.web.util.LogUtils;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by cloud on 2017/3/27.
 */
public abstract class KfkProducer {

    @Autowired
    private KfkConfig kfkConfig;


    public void sendMsg(String msg){
        ProducerRecord<String,String> record = new ProducerRecord<String, String>(kfkConfig.getTopic(),null,msg);
        kfkConfig.getProducer().send(record);

    }

    public void sendMsg(String msg,String topic){
        ProducerRecord<String,String> record = new ProducerRecord<String, String>(topic,null,msg);
        kfkConfig.getProducer().send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if(e != null){
                    e.printStackTrace();
                }else{
                    LogUtils.log.info("send out!");
                }
            }
        });
    }

    public void sendMsg(String msg,Callback callback){
        ProducerRecord<String,String> record = new ProducerRecord<String, String>(kfkConfig.getTopic(),null,msg);
        kfkConfig.getProducer().send(record, callback);
    }

    public void sendMsg(String msg,String topic ,Callback callback){
        ProducerRecord<String,String> record = new ProducerRecord<String, String>(topic,null,msg);
        kfkConfig.getProducer().send(record, callback);
    }
}
