package com.kelvin.app;

import org.junit.Test;
import sun.misc.BASE64Encoder;

import javax.xml.bind.DatatypeConverter;

public class TokenTest {
    @Test
    public void test1() throws Exception {
        byte[] secret = DatatypeConverter.parseBase64Binary("MDk4ZjZiY2Q0NjIxZDM3M2NhZGU0ZTgzMjYyN2I0");
        System.out.println(new String(secret));
    }

    @Test
    public void test2() throws Exception {
        BASE64Encoder base64Encoder = new BASE64Encoder();
        String encode = base64Encoder.encode("098f6bcd4621d373cade4e832627b4".getBytes());
        System.out.println(encode);
    }
}
