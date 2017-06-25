package com.nowcoder.service;

import antlr.StringUtils;
import com.nowcoder.dao.UserDAO;
import com.nowcoder.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import static com.nowcoder.util.WendaUtil.MD5;


/**
 * Created by Damon on 2017/6/23.
 */
@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDAO userDao;

    public Map<String, String> login(String username, String password){

    }

    public Map<String,String> register(String username, String password){
        Map<String, String> map = new HashMap<>();
        if(org.apache.commons.lang.StringUtils.isBlank(username)){
            map.put("msg", "用户名不能为空！");
            return map;
        }

        if(org.apache.commons.lang.StringUtils.isBlank(password)){
            map.put("msg", "密码不能为空！");
            return map;
        }

        User user = userDao.selectByName(username);
        if(user != null){
            map.put("msg", "该用户名已被注册！");
            return map;
        }

        //密码强度
        user = new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        user.setPassword(MD5(password + user.getSalt()));
        userDao.addUser(user);

//        //登入
//        String ticket = addLoginTicket(user.getId());
//        map.put("ticket", ticket);
        return map;
    }

    public User getUser(int id){
        return userDao.selectById(id);
    }
}
