package com.kelvin.app.controller;

import com.kelvin.app.config.Audience;
import com.kelvin.app.util.JwtHelper;
import com.kelvin.app.vo.ReqUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;

@Controller
public class UserController {

    @Autowired
    Audience audience;

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestBody ReqUser reqUser) throws ServletException {
        if (!"kevin".equals(reqUser.getUsername()) || !"123".equals(reqUser.getPassword())){
            throw new ServletException("username or password not correct");
        }

        return JwtHelper.creatJWT(reqUser.getUsername(),"111", "member", audience.getClientId(),
                audience.getName(), audience.getExpiresSecond()*1000, audience.getBase64Secret());
    }
}
