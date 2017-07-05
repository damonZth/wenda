package com.nowcoder.controller;

import com.nowcoder.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Damon on 2017/7/4.
 */
@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserService userService;


    //页面注册功能
    @RequestMapping(path = {"/reg/"}, method = {RequestMethod.POST})
    public String reg(Model model,
                      @RequestParam("username") String username,
                      @RequestParam("password") String password,
                      @RequestParam(value = "rememberme", defaultValue = "false") boolean rememberme,
                      HttpServletResponse response){
//        try{
//            Map<String, String> map = userService.register(username, password);
//            if(map.containsKey("msg")){
//                model.addAttribute("msg", map.get("msg"));
//                return "login";
//            }
//            //注册成功，则直接跳转到首页
//            return "redirect:/";
        try {
            Map<String ,String> map = userService.register(username,password);
//            aa(model, map, response);
//            return "login";
            if(map.containsKey("ticket")){
                Cookie cookie = new Cookie("ticket", map.get("ticket"));
                cookie.setPath("/");

                response.addCookie(cookie);
                return "redirect:/";
            }else{
                model.addAttribute("msg", map.get("msg"));
                return "login";
            }
        }catch (Exception e){
            logger.error("注册异常" + e.getMessage());
            model.addAttribute("msg", "服务器错误");
            return "login";
        }
    }

//    private String aa(Model model,Map<String, String> map,HttpServletResponse response){
//        if(map.containsKey("ticket")){
//            Cookie cookie = new Cookie("ticket", map.get("ticket"));
//            cookie.setPath("/");
//
//            response.addCookie(cookie);
//            return "redirect:/";
//        }else{
//            model.addAttribute("msg", map.get("msg"));
//            return "login";
//        }
//    }

    //页面登入功能
    @RequestMapping(path = {"/login/"}, method = {RequestMethod.POST})
    public String login(Model model,
                        @RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam(value = "rememberme", defaultValue = "false") boolean rememberme,
                        HttpServletResponse response){
        try {
            Map<String ,String> map = userService.login(username,password);
//            aa(model, map, response);
            if(map.containsKey("ticket")){
                Cookie cookie = new Cookie("ticket", map.get("ticket"));
                cookie.setPath("/");
                response.addCookie(cookie);
                return "redirect:/";
            }else{
                model.addAttribute("msg", map.get("msg"));
                return "login";
            }
        }catch (Exception e){
            logger.error("登入异常" + e.getMessage());
            model.addAttribute("msg","服务器错误");
            return "login";
        }
    }

    //登入注册页面
    @RequestMapping(path = {"/reglogin"}, method = {RequestMethod.GET})
    public String regloginPage(Model model){
        return "login";
    }

    @RequestMapping(path = {"/logout"}, method = {RequestMethod.GET})
    public String logout(@CookieValue("ticket") String ticket){
        userService.logout(ticket);
        return "redirect:/";
    }

}
