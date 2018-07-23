package com.kelvin.app.config;

import com.kelvin.app.exception.LoginException;
import com.kelvin.app.util.JwtHelper;
import io.jsonwebtoken.Claims;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilter extends GenericFilterBean {
    @Autowired
    Audience audience;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String authHeader = request.getHeader("authorization");
        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            filterChain.doFilter(request, response);
        } else {
            if(null == authHeader || !authHeader.startsWith("bearer")){
                throw new LoginException("Missing or Invalid token");
            }

            try {
                String token = authHeader.substring(7);
                if(null == audience){
                    WebApplicationContext beanFactory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
                    audience = (Audience) beanFactory.getBean("audience");
                }
                Claims claims = JwtHelper.parseJWT(token, audience.getBase64Secret());
                if(null == claims){
                    throw new LoginException("invalid token");
                }
                request.setAttribute("claims", claims);
                filterChain.doFilter(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                throw new LoginException("login error");
            }
        }
    }
}
