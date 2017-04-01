package com.waixing.service;

import com.waixing.dao.GoodsDao;
import com.waixing.entity.Goods;
import com.waixing.repository.GoodsRepository;
import com.waixing.utils.text.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;
import org.springframework.data.domain.Sort.Direction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yonglang on 2017/3/30.
 */
@Component
public class GoodsService extends BaseService<Goods>{
    private final GoodsDao goodsDao;
    private final MongoTemplate mongoTemplate;
    private final GoodsRepository goodsRepository;
    @Autowired
    public GoodsService(GoodsDao goodsDao, MongoTemplate mongoTemplate, GoodsRepository goodsRepository) {
        super(GoodsService.class);
        this.goodsDao = goodsDao;
        this.mongoTemplate = mongoTemplate;
        this.goodsRepository = goodsRepository;
    }
    /*    新增商品   */
    public Goods addGoods(String userId, String name, long price, String attribute, String status, String address){
        long date = System.currentTimeMillis();
        Goods goods = new Goods(userId, name, price, gson.fromJson(attribute, ArrayList.class), status,
                gson.fromJson(address, Map.class), date, date);
        return goodsRepository.save(goods);
    }
    /*    分页查询商品list    */
    public Map<String, Object> getGoodsList(String name, int page, int rows, String order, String sorts){
        PageRequest pageRequest = getPageRequest(page, rows, order, sorts);
        Criteria criteria = new Criteria();
        if(!TextUtil.isEmpty(name)){
            criteria.and("name").regex(name);
        }
        long count = dao.count(criteria);
        List<Goods> list = dao.getList(criteria, pageRequest);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("rows",list);
        map.put("totals",count);
        return map;
    }
    /*    更新商品资料    */
    public boolean updateGoods(String id, String name, long price, String attribute, String status, String address){
        Criteria criteria = new Criteria("_id").is(id);
        Map<String, Object> map = new HashMap<String, Object>();
        if(!TextUtil.isEmpty(name)){
            map.put("name",name);
        }
        if(!TextUtil.isEmpty(attribute)){
            map.put("attribute",gson.fromJson(attribute,ArrayList.class));
        }
        if(!TextUtil.isEmpty(status)){
            map.put("status",status);
        }
        if(!TextUtil.isEmpty(address)){
            map.put("address",gson.fromJson(address,Map.class));
        }
        map.put("price",price);
        map.put("lastTime",System.currentTimeMillis());
        return dao.update(criteria, map);
    }
    /*   删除商品资料  */
    public boolean deleteGoods(String id){
        Criteria criteria = new Criteria("_id").is(id);
        return dao.delete(criteria);
    }

}
