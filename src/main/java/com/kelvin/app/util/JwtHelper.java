package com.kelvin.app.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.util.Date;

public class JwtHelper {

    public static Claims parseJWT(String token, String base64Security) {
        return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
                .parseClaimsJws(token).getBody();
    }

    public static String creatJWT(String name, String userId, String role, String audience,
                String issuer, long millis, String base64Security) {
        SignatureAlgorithm sign = SignatureAlgorithm.HS256;

        // 生成签发密钥
        byte[] base64Binary = DatatypeConverter.parseBase64Binary(base64Security);
        SecretKeySpec secretKeySpec = new SecretKeySpec(base64Binary, sign.getJcaName());

        JwtBuilder builder = Jwts.builder()
                .setHeaderParam("type", "JWT")
                .claim("role", role)
                .claim("uniqueName", name)
                .claim("userid", userId)
                .setIssuer(issuer)
                .setAudience(audience)
                .signWith(sign, secretKeySpec);

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        if (millis >= 0) {
            long expMillis = nowMillis + millis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp).setNotBefore(now);
        }

        return builder.compact();
    }
}
