package com.waixing.service;

import com.google.gson.Gson;
import com.waixing.dao.BaseDao;
import com.waixing.utils.text.TextUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * service层  基类
 *
 * Created by yonglang on 2017/3/30.
 */
public class BaseService<T>{
    /**
     * json解析工具
     */
    public static Gson gson = new Gson();
    /**
     * log日志
     */
    public static Logger logger;
    /**
     * 集成基类
     */
    @Autowired
    public BaseDao<T> dao;
    /**
     * 通过构造器传对应的log日志监控
     */
    public BaseService(Class c) {
        this.logger = LogManager.getLogger(c);
    }
    /**
     * 分页查询    PageRequest返回
     */
    public PageRequest getPageRequest(int page, int rows, String order, String sorts){
        if (page <= 0) page = 0;
        else page -= 1;
        if (rows <= 0) rows = 10;
        Sort sort = new Sort(Sort.Direction.DESC,"lastTime");
        if(!TextUtil.isEmpty(sorts)){
            sort = new Sort(Sort.Direction.fromString(order),"lastTime",sorts);
        }
        return new PageRequest(page,rows,sort);
    }

}
