package com.waixing.repository;

import com.waixing.entity.Goods;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 商品数据库查询操作
 *
 * Created by yonglang on 2017/3/30.
 */
public interface GoodsRepository extends MongoRepository<Goods, String>{

}
