package com.waixing.test;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.Test;

/**
 * Created by yonglang on 2017/3/15.
 */
public class MongodbTest {
    @Test
    public void test(){
        try {
            //连接mongodb服务
            MongoClient client = new MongoClient("localhost",27017);
            //连接到数据库
            MongoDatabase mgdatabase = client.getDatabase("boot");
            System.out.println("Connection Successful");
            MongoCollection<Document> collection = mgdatabase.getCollection("user");
            /**
             * 检索数据表里面所有文档数据
             * 1. 获取迭代器FindIterable<Document>
             * 2. 获取游标MongoCursor<Document>
             * 3. 通过游标遍历检索出的文档集合
             * */
            FindIterable<Document> findIterable = collection.find();
            MongoCursor<Document> mongoCursor = findIterable.iterator();
            while (mongoCursor.hasNext()){
                System.out.println(mongoCursor.next());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
