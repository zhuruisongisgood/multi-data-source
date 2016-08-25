package com.zk798.user.controller;


import com.zk798.user.dao.common.AreaMapper;
import com.zk798.user.dao.wfs.UserMapper;
import com.zk798.user.pojo.Area;
import com.zk798.user.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
@Slf4j
public class UserController{

    @Autowired
    private AreaMapper areaMapper;
    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/get")
    public String getUser(){

        Area area = areaMapper.selectByPrimaryKey(1);
        log.info("area ={}",area);

        User user = userMapper.selectByPrimaryKey("");
        log.info("user ={}",user);

        return "";
    }


	
	
}
