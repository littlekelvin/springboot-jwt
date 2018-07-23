package com.kelvin.app.controller;

import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/secured")
public class SecuredController {

    @RequestMapping("main")
    @ResponseBody
    public String mainPage(HttpServletRequest request, HttpServletResponse response){
        Claims claims = (Claims) request.getAttribute("claims");
        System.out.println("token->:" + request.getAttribute("claims"));
        return "MainPage";
    }
}
