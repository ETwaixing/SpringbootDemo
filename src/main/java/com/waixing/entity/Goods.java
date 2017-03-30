package com.waixing.entity;

import com.google.gson.Gson;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

/**
 * 商品实体类
 * Created by yonglang on 2017/3/30.
 */
@Document(collection = "goods")
public class Goods {
    private static final Gson gson = new Gson();

    @Id
    private String id;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品价格 Long型 单位为分 好计算
     */
    private Long price;
    /**
     * 商品的属性列表
     */
    private List<Map<String, Object>> attribute;
    /**
     * 商品的状态（默认为0）   0.未上架  1.下架  2.上架
     */
    private String status;
    /**
     * 商品的地址
     */
    private Map<String, Object> address;
    /**
     * 保存时间   毫秒值
     */
    private Long saveTime;
    /**
     * 最后一次操作时间  毫秒值
     */
    private Long lastTime;

    public Goods() {
    }

    public Goods(String userId, String name, Long price, List<Map<String, Object>> attribute, String status, Map<String, Object> address, Long saveTime, Long lastTime) {
        this.userId = userId;
        this.name = name;
        this.price = price;
        this.attribute = attribute;
        this.status = status;
        this.address = address;
        this.saveTime = saveTime;
        this.lastTime = lastTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public List<Map<String, Object>> getAttribute() {
        return attribute;
    }

    public void setAttribute(List<Map<String, Object>> attribute) {
        this.attribute = attribute;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<String, Object> getAddress() {
        return address;
    }

    public void setAddress(Map<String, Object> address) {
        this.address = address;
    }

    public Long getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(Long saveTime) {
        this.saveTime = saveTime;
    }

    public Long getLastTime() {
        return lastTime;
    }

    public void setLastTime(Long lastTime) {
        this.lastTime = lastTime;
    }

    @Override
    public String toString() {
        return gson.toJson(this);
    }

    /**
     * 将对象转化为bson的document文档格式
     *
     * @return  返回转换的数据
     */
    public org.bson.Document toDocument(){
        return org.bson.Document.parse(this.toString());
    }
}
