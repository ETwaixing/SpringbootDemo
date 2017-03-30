package com.waixing.dao;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.waixing.entity.User;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

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
     * @param mongoCollection                    mongo数据库集合（表）
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
    /**
     * 聚合
     *
     * @param operations 聚合操作
     * @return 聚合结果
     */
    public AggregationResults<Document> aggregate(AggregationOperation... operations){
        TypedAggregation<User> typedAggregation = newAggregation(User.class, operations);
        return mongoTemplate.aggregate(typedAggregation,Document.class);
    }
    /**
     * 聚合
     *
     * @param pipeline 聚合操作
     * @return 聚合结果
     */
    public AggregationOutput aggregate(DBObject... pipeline){
        DBCollection collection = mongoTemplate.getCollection(mongoTemplate.getCollectionName(User.class));
        return collection.aggregate(Arrays.asList(pipeline));
    }
    /**
     * 聚合
     *
     * @param pipeline 聚合操作
     * @return 聚合结果
     */
    public List<User> aggregate(Function<Document, User> mapper, Bson... pipeline){
        ArrayList<Document> documents = mongoCollection.aggregate(Arrays.asList(pipeline)).into(new ArrayList<>());
        ArrayList<User> list = new ArrayList<>();
        documents.forEach(document -> list.add(mapper.apply(document)));
        return list;
    }
    //用户数据库操作 聚合---主要用于处理数据(诸如统计平均值,求和等) crud




}
