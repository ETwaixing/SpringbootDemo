package com.waixing.controller;

import com.waixing.entity.User;
import com.waixing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户控制层
 * Created by yonglang on 2017/3/15.
 */
@RestController
@RequestMapping(value="/waixing/user")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @RequestMapping(value="/test")
    public String test(){
        return "ok";
    }
    @RequestMapping(value="/add")
    public String addUser(){
        return userService.addUser();
    }
    @RequestMapping(value="/find")
    public List<User> findAll(){
        return userService.findAllUser();
    }
    @RequestMapping(value="/findLike")
    public List<User> findByNameLike(String name){
        return userService.findByNameLike(name);
    }
    @RequestMapping(value="/findAge")
    public List<User> findAge(){ return userService.findByName(); }
    @RequestMapping(value="/updateUser")
    public String updateUser(){ return userService.updateUser(); }
    @RequestMapping(value="/totalMoney")
    public int getAllMoney(){ return userService.aggregationUserMoneyTotal(); }
    @RequestMapping(value="/avgMoney")
    public Double getAvgMoney(){ return userService.aggregationUserMoneyAge(); }

}
