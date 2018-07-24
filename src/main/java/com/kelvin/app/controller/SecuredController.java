package com.kelvin.app.controller;

import com.kelvin.app.config.JwtSetting;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/secured")
public class SecuredController {
    @Autowired
    JwtSetting jwtSetting;

    @RequestMapping("/homePage")
    public String mainPage(HttpServletRequest request){
        Claims claims = (Claims) request.getAttribute("claims");
        System.out.println("token claims->:" + claims);
        return "home";
    }

    @RequestMapping("/getJwtInfo")
    @ResponseBody
    public JwtSetting getJwtInfo() {
        return jwtSetting;
    }
}
