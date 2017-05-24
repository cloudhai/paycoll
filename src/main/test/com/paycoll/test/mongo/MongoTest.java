package com.paycoll.test.mongo;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by cloud on 2017/5/19.
 */
public class MongoTest {
    private MongoDatabase database;

    public MongoTest(){
        init("test");
    }
    public static void main(String[] args) {
        MongoTest test = new MongoTest();
        test.insert("kitty","2",31);
        test.find("cloud");
    }

    public void insert(String name,String userId,int age){
        Document doc = new Document("name",name);
        doc.append("userId",userId);
        doc.append("age",age);
        doc.append("createTm",new Date());
        database.getCollection("user").insertOne(doc);
        doc = null;
    }

    public void find(String name){
        BasicDBObject query = new BasicDBObject("nickName",name);
        FindIterable<Document> result = database.getCollection("user").find(query);
        MongoCursor<Document> cursor = result.iterator();
        while(cursor.hasNext()){
            Document doc = cursor.next();
            System.out.println(doc.toString());
        }
    }


    private void init(String dbName){
        ServerAddress server1 = new ServerAddress("10.116.37.21",27017);
        ServerAddress server2 = new ServerAddress("10.116.95.4",27017);
        List<ServerAddress> addrs = new ArrayList<>(Arrays.asList(server1,server2));
        MongoCredential credential = MongoCredential.createScramSha1Credential("dev",dbName,"devtest".toCharArray());
//        MongoCredential credential = MongoCredential.createCredential("dev",dbName,"devtest".toCharArray());
        List<MongoCredential> credentials = new ArrayList<>(Arrays.asList(credential));
        MongoClientOptions options = MongoClientOptions.builder().build();
        MongoClient client = new MongoClient(addrs,credentials,options);
        MongoDatabase database = client.getDatabase(dbName);
        this.database = database;
    }


}
