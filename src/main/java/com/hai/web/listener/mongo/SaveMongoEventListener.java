package com.hai.web.listener.mongo;

import com.hai.web.annotation.GeneratedValue;
import com.hai.web.model.mongo.SequenceId;
import com.hai.util.LogUtils;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.event.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.ReflectionUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;

/**
 * Created by cloud on 2017/5/7.
 */
public class SaveMongoEventListener extends AbstractMongoEventListener<Object> {
    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {
       final Object source = event.getSource();
       if(source != null){
           LogUtils.log.info(source.toString());
           ReflectionUtils.doWithFields(source.getClass(), new ReflectionUtils.FieldCallback() {
               @Override
               public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                   ReflectionUtils.makeAccessible(field);
                   if(field.isAnnotationPresent(GeneratedValue.class)){
                       long id = getId(source.getClass().getName());
                       field.set(source,id);
                   }
               }
           });
       }
    }

//    @Override
//    public void onApplicationEvent(MongoMappingEvent<?> event) {
//        System.out.println("application event");
//    }
//
//    @Override
//    public void onBeforeSave(BeforeSaveEvent<Object> event) {
//        System.out.println("before savev");
//    }
//
//    @Override
//    public void onAfterSave(AfterSaveEvent<Object> event) {
//        System.out.println("after save");
//    }
//
//    @Override
//    public void onAfterLoad(AfterLoadEvent<Object> event) {
//        System.out.println("after load");
//    }
//
//    @Override
//    public void onAfterConvert(AfterConvertEvent<Object> event) {
//        System.out.println("after convert");
//    }
//
//    @Override
//    public void onAfterDelete(AfterDeleteEvent<Object> event) {
//        System.out.println("after delete");
//    }

    private long getId(String name){
        Query query = new Query(Criteria.where("name").is(name));
        Update update = new Update();
        update.inc("seqId",1);
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.upsert(true);
        options.returnNew(true);
        SequenceId seq = mongoTemplate.findAndModify(query,update,options,SequenceId.class);
        if(seq == null){
            seq = new SequenceId();
            seq.setSeqId(1);
            seq.setName(name);
            mongoTemplate.save(seq);
            return 1L;
        }
        return  seq.getSeqId();
    }
}
