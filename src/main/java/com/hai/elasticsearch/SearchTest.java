package com.hai.elasticsearch;

import com.hai.web.model.TestModel;
import org.apache.ibatis.mapping.MappedStatement;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * Created by cloud on 2017/6/8.
 */
public class SearchTest {
    private final static String CLUSTER_NAME = "kp-elastic";
    private final static String IP = "10.44.131.232";
    private final static int port = 9300;
    private TransportClient client;
    public void init () throws UnknownHostException{
        Settings settings = Settings.builder()
                .put("cluster.name",CLUSTER_NAME)
                .put("client.transport.sniff",true)
                .build();
        client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(IP),port));
//        GetResponse response = client.prepareGet("iktest","text","1").execute().actionGet();
//        System.out.println(response.getSourceAsString());
    }

    public void printHis(SearchHit hit){
        Map<String,Object> map = hit.getSource();
        System.out.println(map);
    }

    public void close(){
        if(client != null){
            client.close();
        }
    }

    public XContentBuilder getJson(String title,String msg){
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("title",title)
                    .field("msg",msg)
                    .endObject();
            return builder;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addIndex(String title,String msg){
        IndexResponse response = client.prepareIndex("iktest","text")
                .setSource(getJson(title,msg))
                .get();
        System.out.println("response:"+response.status().toString());
    }

    public void getIndex(String id){
        GetResponse response = client.prepareGet("iktest", "text",id).execute().actionGet();
        if(response.isExists()){
            System.out.println(response.toString());
        }
    }

    public void search(){
        SearchResponse response = client.prepareSearch("iktest")
                .get();
        for(SearchHit hit : response.getHits()){
            System.out.println(hit.getSourceAsString());
        }
    }

    public void matchall(){
        QueryBuilder query = QueryBuilders.matchQuery("msg","中国");
        SearchResponse response = client.prepareSearch("iktest").setTypes("text")
                .setQuery(query).get();
        for(SearchHit hit : response.getHits()){
            printHis(hit);
        }
    }

    public void term(){
        QueryBuilder query = QueryBuilders.termQuery("msg","公司");
        SearchResponse response = client.prepareSearch("iktest").setTypes("text")
                .setQuery(query).get();
        for(SearchHit hit : response.getHits()){
            printHis(hit);
        }
    }


    public static void main(String[] args) throws IOException {
        SearchTest test = new SearchTest();
        test.init();
        test.matchall();
        test.term();
        test.close();

    }
}
