package com.debug.pmp.server.controller;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author baoguangyu
 * @Date 2021/5/30 9:29
 * @Version 1.0
 */
@Controller
public class SysPageController {


    @RequestMapping(value = {"index.html","/"} )
    public String index(){
        return "index";
    }

    @RequestMapping("login.html")
    public String login(){
        return "login";
    }

    @RequestMapping("main.html")
    public String main(){
        return "main";
    }

    @RequestMapping("404.html")
    public String notFound(){
        return "404";
    }


}
