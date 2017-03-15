package com.waixing.service;

import com.waixing.dao.UserDao;
import com.waixing.entity.User;
import com.waixing.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

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
}
