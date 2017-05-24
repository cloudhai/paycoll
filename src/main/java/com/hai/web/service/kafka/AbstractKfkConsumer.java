package com.hai.web.service.kafka;


import com.hai.web.util.LogUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by cloud on 2017/3/27.
 */
public abstract class AbstractKfkConsumer implements InitializingBean{
    @Autowired
    private KfkConfig kfkConfig;

    private Executor executor;


    @Override
    public void afterPropertiesSet() throws Exception {
        executor = Executors.newFixedThreadPool(kfkConfig.getThreadn(), Executors.defaultThreadFactory());
        executor.execute(new Runnable() {
            KafkaConsumer<String,String> kc = kfkConfig.getConsumer();
            @Override
            public void run() {
                LogUtils.log.info("kafka consumer is run....");
                while(true){
                    ConsumerRecords<String,String> records = kc.poll(1000);
                    for(ConsumerRecord<String,String> record:records){
                        executor.execute(new KfkTask(record));
                    }
                }
            }
        });
    }

    protected abstract void messageHandler(String msg);

    class KfkTask implements Runnable{
        private ConsumerRecord<String,String> record;
        public KfkTask(ConsumerRecord<String,String> record){
            this.record = record;
        }
        @Override
        public void run() {
            messageHandler(record.value());
        }
    }
}
