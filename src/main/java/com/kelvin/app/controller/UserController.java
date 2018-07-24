package com.kelvin.app.controller;

import com.kelvin.app.config.JwtSetting;
import com.kelvin.app.util.JwtHelper;
import com.kelvin.app.vo.ReqUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class UserController {

    @Autowired
    JwtSetting jwtSetting;

    @PostMapping("/login")
    @ResponseBody
    public void login(ReqUser reqUser, HttpServletResponse response) throws ServletException, IOException {
        if (!"kelvin".equals(reqUser.getUsername()) || !"123".equals(reqUser.getPassword())){
            throw new ServletException("username or password not correct");
        }
        String jwtToken = JwtHelper.createJWT(reqUser.getUsername(), "maike", "admin", jwtSetting.getClientId(),
                jwtSetting.getName(), jwtSetting.getExpiresSecond() * 1000, jwtSetting.getBase64Secret());
        Cookie cookie = new Cookie("JWT_TOKEN", jwtToken);
        cookie.setPath("/");
        cookie.setDomain("localhost");
        response.addCookie(cookie);
        response.sendRedirect("/secured/homePage");
    }

    @RequestMapping(value = "/toLogin", method = RequestMethod.GET)
    public String toLogin() {
        return "login";
    }

    @RequestMapping(value = "/homePage", method = RequestMethod.GET)
    public String home() {
        return "home";
    }
}
