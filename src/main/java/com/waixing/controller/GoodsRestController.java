package com.waixing.controller;

import com.google.gson.Gson;
import com.waixing.entity.Goods;
import com.waixing.entity.back.BackMessage;
import com.waixing.service.GoodsService;
import com.waixing.utils.text.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by yonglang on 2017/3/30.
 */
@RestController
@RequestMapping(value="/waixing/Goods")
public class GoodsRestController extends BaseController<Goods>{
    private final GoodsService goodsService;
    @Autowired
    public GoodsRestController(GoodsService goodsService) {
        super(GoodsRestController.class);
        this.goodsService = goodsService;
    }

    /**
     *    新增商品
     */
    @RequestMapping(value="/addGoods.json")
    public BackMessage addGoods(String userId, String name, long price, String attribute, String status, String address){
        if(TextUtil.isEmpty(userId)){
            return returnFaild("0","用户id为空");
        }
        try {
            goodsService.addGoods(userId, name, price, attribute, status, address);
            return returnSuccess("1","新增成功");
        }catch (Exception e){
            logger.error(e);
            return returnFaild("0","新增失败");
        }
    }
    /**
     *    商品列表查询
     */
    @RequestMapping(value = "/getGoodsList.json")
    public BackMessage getGoodsList(String name, int page, int rows, String order, String sorts){
        try {
            Map<String, Object> map = goodsService.getGoodsList(name, page, rows, order, sorts);
            return returnSuccess("1","查询成功",map);
        }catch (Exception e){
            logger.error(e);
            return returnFaild("0","查询失败");
        }
    }
    /**
     *     商品信息更新
     */
    @RequestMapping(value = "/updateGoods.json")
    public BackMessage updateGoods(String id, String name, long price, String attribute, String status, String address){
        if(TextUtil.isEmpty(id)){
            return returnFaild("0","商品id为空");
        }
        try {
            boolean update = goodsService.updateGoods(id, name, price, attribute, status, address);
            if(update){
                return returnSuccess("1","更新成功",update);
            }else {
                return returnFaild("0","更新失败");
            }
        }catch (Exception e){
            logger.error(e);
            return returnFaild("0","更新失败");
        }
    }
    /**
     *    商品删除
     */
    public BackMessage deleteGoods(String id){
        if(TextUtil.isEmpty(id)){
            return returnFaild("0","商品id为空");
        }
        try {
            boolean delete = goodsService.deleteGoods(id);
            if(delete){
                return  returnSuccess("1","删除成功",delete);
            }else {
                return returnFaild("0","删除失败");
            }
        }catch (Exception e){
            logger.error(e);
            return returnFaild("0","删除失败");
        }
    }

}
