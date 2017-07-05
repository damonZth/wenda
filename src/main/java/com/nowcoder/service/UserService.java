package com.nowcoder.service;

import com.nowcoder.dao.LoginTicketDAO;
import com.nowcoder.dao.UserDAO;
import com.nowcoder.model.LoginTicket;
import com.nowcoder.model.User;
import com.nowcoder.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.nowcoder.util.WendaUtil.MD5;


/**
 * Created by Damon on 2017/6/23.
 */
@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDAO userDao;

    @Autowired
    private LoginTicketDAO loginTicketDao;

    /**
     * 登入账号
     * 对用户输入的账号、密码进行检验并有一个map来存储判断的输出
     * @param username 输入账号
     * @param password 输入密码
     * @return
     */
    public Map<String, String> login(String username, String password){
        Map<String, String> map = new HashMap<>();
        if(org.apache.commons.lang.StringUtils.isBlank(username)){
            //判断密码是否为空，如果为空，则提示用户名不能为空
            map.put("msg", "用户名不能为空！");
            return map;
        }
        if(org.apache.commons.lang.StringUtils.isBlank(password)){
            //判断密码是否为空，如果为空，则提示密码不能不空
            map.put("msg", "密码不能为空！");
            return map;
        }
        User user = userDao.selectByName(username);
        if(user == null){
            //判断用户名是否存在
            map.put("msg", "用户名不存在");
            return map;
        }
        if(!WendaUtil.MD5(password + user.getSalt()).equals(user.getPassword())){
            //验证密码
            map.put("msg","密码错误");
            return map;
        }
        String ticket = addLoginTicket(user.getId());
        map.put("ticket",ticket);
        return map;
    }

    private String addLoginTicket(int userId){
        LoginTicket ticket = new LoginTicket();
        ticket.setUserId(userId);
        Date date = new Date();
        date.setTime(3600 * 24 * 10 + date.getTime());
        ticket.setExpired(date);
        ticket.setStatus(0);
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-",""));
        loginTicketDao.addTicket(ticket);
        return ticket.getTicket();
    }


    /**
     * 注册账号
     * @param username 输入用户名
     * @param password 输入用户密码
     * @return
     */
    public Map<String,String> register(String username, String password){
        Map<String, String> map = new HashMap<>();
        if(org.apache.commons.lang.StringUtils.isBlank(username)){
            //判断密码是否为空，如果为空，则提示用户名不能为空
            map.put("msg", "用户名不能为空！");
            return map;
        }
        if(org.apache.commons.lang.StringUtils.isBlank(password)){
            //判断密码是否为空，如果为空，则提示密码不能不空
            map.put("msg", "密码不能为空！");
            return map;
        }
        User user = userDao.selectByName(username);
        if(user != null){
            //判断用户名是否已被注册
            map.put("msg", "该用户名已被注册！");
            return map;
        }
        //密码强度
        user = new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0,5));//随机生成5个字符的salt，加强密码强度
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));//生成随机的图像
        user.setPassword(MD5(password + user.getSalt()));//用户输入的密码 + salt 使得密码强度更大
        userDao.addUser(user);

        String ticket = addLoginTicket(user.getId());
        map.put("ticket",ticket);
        return map;
    }

    public User getUser(int id){
        return userDao.selectById(id);
    }

    //退出登入，使ticket失效
    public void logout(String ticket){
        loginTicketDao.updateStatus(ticket,1);
    }

}
