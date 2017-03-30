package com.waixing.controller;

import com.google.gson.Gson;
import com.waixing.entity.back.BackMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

/**
 * 抽象控制器  公共方法集合
 * Created by yonglang on 2017/3/20.
 */
public class BaseController {
    /**
     * log日志
     */
    public static Logger logger;

    public BaseController(Class c) {
        this.logger = LogManager.getLogger(c);
    }
    /**
     *  返回成功信息
     */
    public static BackMessage returnSuccess(String code, String msg,Object data){
        return new BackMessage(true, code, msg, data);
    }
    /**
     *   返回失败信息
     */
    public static BackMessage returnFaild(String code, String msg){
        return new BackMessage(false, code, msg);
    }


}
