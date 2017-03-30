package com.waixing.dao;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.waixing.utils.reflection.ReflectionUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

/**
 * DAO数据操作基类
 *
 * Created by yonglang on 2017/3/30.
 */
public class BaseDao<T> {
    protected final MongoTemplate mongoTemplate;
    protected final MongoCollection<Document> mongoCollection;
    protected final MongoClientURI mongoClientURI;
    protected final MongoClient mongoClient;
    @Autowired
    public BaseDao(MongoTemplate mongoTemplate, MongoClientURI mongoClientURI, MongoClient mongoClient) {
        this.mongoTemplate = mongoTemplate;
        this.mongoClientURI = mongoClientURI;
        this.mongoClient = mongoClient;
        this.mongoCollection = mongoClient.getDatabase(mongoClientURI.getDatabase()).getCollection(mongoTemplate.getCollectionName(this.getEntityClass()));
    }
    /**
     * 获取需要操作的实体类class
     */
    private Class<T> getEntityClass() {
        return ReflectionUtils.getSuperClassGenricType(getClass());
    }
    /**
     * 聚合
     *
     * @param operations 聚合操作
     * @return 聚合结果
     */
    public AggregationResults<Document> aggregate(AggregationOperation... operations){
        TypedAggregation<T> typedAggregation = newAggregation(this.getEntityClass(), operations);
        return mongoTemplate.aggregate(typedAggregation,Document.class);
    }
    /**
     * 聚合
     *
     * @param pipeline 聚合操作
     * @return 聚合结果
     */
    public AggregationOutput aggregate(DBObject... pipeline){
        DBCollection collection = mongoTemplate.getCollection(mongoTemplate.getCollectionName(this.getEntityClass()));
        return collection.aggregate(Arrays.asList(pipeline));
    }
    /**
     * 聚合
     *
     * @param pipeline 聚合操作
     * @return 聚合结果
     */
    public List<T> aggregate(Function<Document, T> mapper, Bson... pipeline){
        ArrayList<Document> documents = mongoCollection.aggregate(Arrays.asList(pipeline)).into(new ArrayList<>());
        ArrayList<T> list = new ArrayList<>();
        documents.forEach(document -> list.add(mapper.apply(document)));
        return list;
    }
    /**
     *     通用获取count值
     */
    public long count(Criteria criteria){
        return mongoTemplate.count(Query.query(criteria),this.getEntityClass());
    }
    /**
     *     通用分页查询
     */
    public List<T> getList(Criteria criteria, PageRequest pageRequest){
        return mongoTemplate.find(Query.query(criteria).with(pageRequest),this.getEntityClass());
    }
    /**
     *     通用更新
     */
    public boolean update(Criteria criteria, Map<String, Object> map){
        Update update = new Update();
        for (Map.Entry<String, Object> entry:map.entrySet()) {
            update.set(entry.getKey(), entry.getValue());
        }
        return mongoTemplate.updateFirst(Query.query(criteria), update, this.getEntityClass()).isUpdateOfExisting();
    }
    /**
     *     通用删除
     */
    public boolean delete(Criteria criteria){
        return mongoTemplate.remove(Query.query(criteria),this.getEntityClass()).isUpdateOfExisting();
    }



}
