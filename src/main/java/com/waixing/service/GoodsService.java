package com.waixing.service;

import com.mongodb.DBObject;
import com.mongodb.Function;
import com.waixing.dao.GoodsDao;
import com.waixing.entity.Goods;
import com.waixing.repository.GoodsRepository;
import com.waixing.utils.text.TextUtil;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.mongodb.client.model.Filters.*;

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
    /*    新增商品   insert 与  save的区别  */
    /*   insert效率高 不会遍历整个表    save会遍历整个表 存在相同的即更新      */
    public void addGoods(String userId, String name, long price, String attribute, String status, String address){
        long date = System.currentTimeMillis();
        Goods goods = new Goods(userId, name, price, gson.fromJson(attribute, ArrayList.class), status,
                gson.fromJson(address, Map.class), date, date);
        mongoTemplate.insert(goods);
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
    /*    聚合统计 根据用户id分组求平均值 ----  简单常规聚合 */
    public List<Document> getAvgByUserId(String status){
        Criteria criteria = new Criteria("status").is(status);
        MatchOperation match = Aggregation.match(criteria);
        GroupOperation group = Aggregation.group("userId").first("userId").as("userId").sum("price").as("sum").avg("price").as("avg").count().as("count");
        ProjectionOperation project = Aggregation.project("userId","sum","count","avg");
        TypedAggregation<Goods> typedAggregation = Aggregation.newAggregation(Goods.class, match, group, project);
        AggregationResults<Document> results = mongoTemplate.aggregate(typedAggregation, Document.class);
        List<Document> list = new ArrayList<>();
        results.forEach(document -> list.add(document));
        return list;
    }
    /*  聚合统计 根据用户id分组求平均值 ----  利用Bson进行聚合     */
    public List<Document> getAvgByUserIdAndBson(String status){
        Criteria criteria = new Criteria("status").is(status);
        Map map = criteria.getCriteriaObject().toMap();
        Bson matchBson = new Document(map);
        Bson match = eq("$match", matchBson);
        Bson group = eq("$group", and(eq("_id", "$userId"), eq("sum", eq("$sum", "$price")),
                eq("count", eq("$sum", 1)), eq("avg", eq("$avg", "$price"))));
        Bson project =eq("$project",and(eq("_id",0),eq("sum",1),eq("count",1),
                eq("avg",1),eq("date",new Date())));
        return dao.aggregete(match, group, project);
//                 Function 赋值？
//        Function<Document, CompleteLog> mapper = doc -> {
//            CompleteLog completeLog = new CompleteLog();
//            Document completeLogtemp = doc.get("CompleteLogList", Document.class);
//            completeLog.setPreCheckStatus(completeLogtemp.getString("preCheckStatus"));
//            completeLog.setPreCheckName(completeLogtemp.getString("preCheckName"));
//            completeLog.setVipLevel(completeLogtemp.getInteger("vipLevel", 0));
//            try {
//                String settledTime = String.valueOf(completeLogtemp.get("settledTime")) ;
//                if (!isEmpty(settledTime)) {
//                    completeLog.setSettledTime(Long.parseLong(settledTime));
//                }
//            }catch (Exception e){
//                logger.warn("异常字段为：settledTime"+"异常数据id为："+completeLogtemp.getObjectId("id").toString());
//                logger.warn("类型转换错误:"+e.getMessage());
//            }
//            ArrayList submitContent = completeLogtemp.get("submitContent", ArrayList.class);
//            JSONArray jsonArray = JSONArray.fromObject(submitContent);
//            completeLog.setSubmitContent(jsonArray);
//            return completeLog;
//        };
    }



}
