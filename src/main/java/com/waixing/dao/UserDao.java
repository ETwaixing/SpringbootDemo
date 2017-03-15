package com.waixing.dao;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.waixing.entity.User;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

/**
 * 用户数据库curd操作
 * Created by yonglang on 2017/3/15.
 */
@Component
public class UserDao {
    /**
     * 自动装配构造函数
     * @param mongoClient          mongo数据库服务对象
     * @param mongoClientURI      mongo数据库地址对象
     * @param mongoTemplate             mongo数据库模板对象
     * mongoCollection                    mongo数据库集合（表）
     */
    private final MongoTemplate mongoTemplate;
    private final MongoClientURI mongoClientURI;
    private final MongoClient mongoClient;
    private final MongoCollection<Document> mongoCollection;
    @Autowired
    public UserDao(MongoTemplate mongoTemplate, MongoClientURI mongoClientURI, MongoClient mongoClient) {
        this.mongoTemplate = mongoTemplate;
        this.mongoClientURI = mongoClientURI;
        this.mongoClient = mongoClient;
        mongoCollection = mongoClient.getDatabase(mongoClientURI.getDatabase()).getCollection(mongoTemplate.getCollectionName(User.class));
    }
    //用户数据库操作 聚合---主要用于处理数据(诸如统计平均值,求和等) crud


}
