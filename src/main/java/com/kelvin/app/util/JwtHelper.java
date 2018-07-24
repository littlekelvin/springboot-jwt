package com.kelvin.app.util;

import com.kelvin.app.exception.AuthenticationException;
import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.util.Date;

public class JwtHelper {

    public static Claims parseJWT(String token, String base64Security) {
        try {
            //OK, we can trust this JWT
            return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
                    .parseClaimsJws(token).getBody();
        } catch (Exception e) {
            //Not OK, we can not trust this JWT
            throw new AuthenticationException("Authentication Failed");
        }
    }

    public static String createJWT(String name, String userId, String role, String audience,
                                   String issuer, long expireMillis, String base64Security) {

        // 生成签发密钥
        byte[] base64Binary = DatatypeConverter.parseBase64Binary(base64Security);
        SecretKeySpec secretKey = new SecretKeySpec(base64Binary, SignatureAlgorithm.HS256.getJcaName());

        JwtBuilder builder = Jwts.builder()
                .setHeaderParam("type", "JWT")
                .claim("role", role)
                .claim("name", name)
                .claim("userId", userId)
                .setIssuer(issuer)
                .setAudience(audience)
                .signWith(SignatureAlgorithm.HS256, secretKey);

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        if (expireMillis >= 0) {
            long expMillis = nowMillis + expireMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp).setNotBefore(now);
        }

        return builder.compact();
    }
}
