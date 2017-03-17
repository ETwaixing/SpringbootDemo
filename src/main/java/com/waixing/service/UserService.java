package com.waixing.service;

import com.waixing.dao.UserDao;
import com.waixing.entity.User;
import com.waixing.repository.UserRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

/**
 * 用户service层
 * Created by yonglang on 2017/3/15.
 */
@Component
public class UserService {
    private final MongoTemplate mongoTemplate;
    private final UserRepository userRepository;
    private final UserDao userDao;
    @Autowired
    public UserService(MongoTemplate mongoTemplate, UserRepository userRepository,UserDao userDao) {
        this.mongoTemplate = mongoTemplate;
        this.userRepository = userRepository;
        this.userDao = userDao;
    }

    //聚合操作
    public AggregationResults<Document> aggregate(AggregationOperation... operations){
        TypedAggregation<User> userTypedAggregation = newAggregation(User.class, operations);
        return mongoTemplate.aggregate(userTypedAggregation, Document.class);
    }



    //用户新增
    public String addUser(){
        String[] name = {"李","张","王","郭"};
        User user = new User();
        user.setAge((int)(Math.random()*100)+"");
        user.setName(name[(int)(Math.random()*4)]+((int)(Math.random()*10)+""));
        userRepository.insert(user);
        return "OK";
    }
    //查询所有用户
    public List<User> findAllUser(){
        return userRepository.findAll();
    }
    //模糊查找姓名like
    public List<User> findByNameLike(String name){
        return userRepository.findByNameLike(name);
    }
    //特定查找
    public List<User> findByName(){
        Criteria criteria = new Criteria("name").regex("郭").and("age").is("22");
        return mongoTemplate.find(Query.query(criteria),User.class);
    }
    //更新
    public String updateUser(){
        Criteria criteria = new Criteria("name").regex("郭").and("age").is("78");
        Update update = new Update();
        update.set("money",20);
        mongoTemplate.updateMulti(new Query(),update,User.class);
        //mongoTemplate.updateFirst(new Query(criteria),update,User.class);
        return "OK";
    }
    //聚合统计查询 sum 和
    public int aggregationUserMoneyTotal(){
        Criteria criteria = new Criteria("name").regex("郭");
        MatchOperation matchOperation = match(criteria);
        GroupOperation groupOperation = group().sum("money").as("money");
        Document result = aggregate(matchOperation, groupOperation).getUniqueMappedResult();
        return result.getInteger("money",0);
    }
    //聚合统计查询 age 平均值
    public Double aggregationUserMoneyAge(){
        MatchOperation matchOperation = match(new Criteria());
        GroupOperation groupOperation = group().avg("money").as("money");
        Document result = aggregate(matchOperation, groupOperation).getUniqueMappedResult();
        return result.getDouble("money");
    }

}
