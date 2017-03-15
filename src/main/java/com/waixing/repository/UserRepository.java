package com.waixing.repository;

import com.waixing.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * 用户数据库基本查询操作
 * Created by yonglang on 2017/3/15.
 */
public interface UserRepository extends MongoRepository<User,String>{
    //支持关键字查询方法--实质value就是mongodb查询代码
    @Query(value="{'name':{$regex:?0}}")
    public List<User> findByNameLike(String name);


}
