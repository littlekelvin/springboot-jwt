package com.kelvin.app;

import org.junit.Test;
import sun.misc.BASE64Encoder;

import javax.xml.bind.DatatypeConverter;

public class TokenTest {
    @Test
    public void test1() throws Exception {
        byte[] secret = DatatypeConverter.parseBase64Binary("eyJyb2xlIjoiYWRtaW4iLCJuYW1lIjoia2VsdmluIiwidXNlcmlkIjoibWFpa2UiLCJpc3MiOiJzZXJ2ZXIiLCJhdWQiOiJhdWRpZW5jZSIsImV4cCI6MTUzMjM1NDE3MywibmJmIjoxNTMyMzU0MTY4fQ");
        System.out.println(new String(secret));
    }

    @Test
    public void test2() throws Exception {
        BASE64Encoder base64Encoder = new BASE64Encoder();
        String encode = base64Encoder.encode("098f6bcd4621d373cade4e832627b4".getBytes());
        System.out.println(encode);
    }
}
