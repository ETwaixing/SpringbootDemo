package com.waixing.dao;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.waixing.entity.Goods;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;


/**
 * 商品表  数据库操作
 *
 * Created by yonglang on 2017/3/30.
 */
@Component
public class GoodsDao extends BaseDao<Goods>{

    public GoodsDao(MongoTemplate mongoTemplate, MongoClientURI mongoClientURI, MongoClient mongoClient) {
        super(mongoTemplate, mongoClientURI, mongoClient);
    }

}
