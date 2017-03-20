package com.waixing.controller;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

/**
 * 抽象控制器  公共方法集合
 * Created by yonglang on 2017/3/20.
 */
public class BaseController {

    public void getMap(String... names){
        for (String name:names) {
            System.out.println(name);
        }
    }

}
