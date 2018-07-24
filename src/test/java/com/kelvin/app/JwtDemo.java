package com.kelvin.app;

import com.kelvin.app.util.JwtHelper;
import io.jsonwebtoken.Claims;
import org.junit.Test;
import sun.misc.BASE64Encoder;

import static org.junit.Assert.assertNotNull;

public class JwtDemo {
    private String secret = new BASE64Encoder().encode("test123".getBytes());

    @Test
    public void createToken() {
        String jwtToken = JwtHelper.createJWT("kelvin", "maike", "admin", "audience", "server",
                50000, secret);

        System.out.println(jwtToken);
    }

    @Test
    public void parseToken() {
        String token = "eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFMyNTYifQ.eyJyb2xlIjoiYWRtaW4iLCJuYW1lIjoia2VsdmluIiwidXNlcklkIjoibWFpa2UiLCJpc3MiOiJzZXJ2ZXIiLCJhdWQiOiJhdWRpZW5jZSIsImV4cCI6MTUzMjM1NDUxNiwibmJmIjoxNTMyMzU0NDY2fQ.MtmZMOOlNAew4kyrJoc4SrDoZ4LasG3qFcUobhmakk0";
        Claims claims = JwtHelper.parseJWT(token, secret);

        assertNotNull(claims);
        System.out.println("issuer: " + claims.getIssuer());
        System.out.println("userId: " + claims.get("userId", String.class));
    }
}
