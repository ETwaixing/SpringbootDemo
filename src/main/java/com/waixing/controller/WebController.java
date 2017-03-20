package com.waixing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 视图解析器配置 返回页面显示
 * Created by yonglang on 2017/3/20.
 */
@Controller
@RequestMapping(value="/waixing")
public class WebController {

    @RequestMapping(value="/index",method = RequestMethod.GET)
    public String index(){
        System.out.println("123");
        return "index";
    }

    @RequestMapping(value="/second",method = RequestMethod.GET)
    public String second(){
        return "second";
    }

}
