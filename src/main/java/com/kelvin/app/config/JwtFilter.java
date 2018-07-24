package com.kelvin.app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kelvin.app.exception.AuthenticationException;
import com.kelvin.app.util.JwtHelper;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilter extends GenericFilterBean {
    JwtSetting jwtSetting;
    ObjectMapper objectMapper;

    public JwtFilter(JwtSetting jwtSetting, ObjectMapper objectMapper) {
        this.jwtSetting = jwtSetting;
        this.objectMapper = objectMapper;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            filterChain.doFilter(request, response);
        } else {
            try {
                String headerPayload = request.getHeader("authentication");
                if(null == headerPayload || "".equals(headerPayload)) {
                    String tokenFromCookie = getTokenFromCookie(request);
                    if(null != tokenFromCookie){
                        headerPayload = "Bearer " + tokenFromCookie;
                    }
                }
                if(null == headerPayload || "".equals(headerPayload) || !headerPayload.startsWith("Bearer ")) {
                    throw  new AuthenticationException("Invalid Token");
                }

                String token = headerPayload.substring(7);
                Claims claims = JwtHelper.parseJWT(token, jwtSetting.getBase64Secret());
                if(null == claims){
                    throw new AuthenticationException("invalid token");
                }
                request.setAttribute("claims", claims);
                filterChain.doFilter(request, response);
            } catch (AuthenticationException e) {
                objectMapper.writeValue(response.getWriter(), "Authentication Failed! Please Login first.");
            }
        }
    }

    private String getTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(null == cookies){
            return null;
        }
        for(Cookie cookie : cookies) {
            if("JWT_TOKEN".equalsIgnoreCase(cookie.getName())){
                return cookie.getValue();
            }
        }
        return null;
    }
}
