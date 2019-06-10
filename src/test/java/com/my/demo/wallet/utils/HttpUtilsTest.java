package com.my.demo.wallet.utils;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class HttpUtilsTest {
    @Test
    public void testGet() {
        String uri = "http://47.52.46.240:8899/api/v1/user/info/wallet";
        Map<String, String> headres = new HashMap<>();
        headres.put("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJvcmlnaW4iOjEsInVzZXJBY2NvdW50IjoiMTM5KioqOTk5MiIsInVzZXJpZCI6MTAzMywibWlsbGlzIjoiMTU1OTY5OTcxNzMyOCIsImlzcyI6ImdvbGRib3hhcGl1c2VyIiwiYXVkIjoiOXE2aHphcTZjZmI0cWRia3UxOXQxY29xZTQydTQzYnYiLCJleHAiOjE1NTk3MDY5MTcsIm5iZiI6MTU1OTY5OTcxN30.IGv0eKhIkpGHFc3YwWZiagF4UwlLEome9b76MCobWN8");
        String result = HttpUtils.get(uri, headres);
        System.out.println(result);
    }

    @Test
    public void testPost() {
        String uri = "http://47.52.46.240/api/v1/sendVerifyCode";
        Map<String, String> headres = new HashMap<>();
        headres.put("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJvcmlnaW4iOjEsInVzZXJBY2NvdW50IjoiMTM5KioqOTk5MiIsInVzZXJpZCI6MTAzMywibWlsbGlzIjoiMTU1OTY5OTcxNzMyOCIsImlzcyI6ImdvbGRib3hhcGl1c2VyIiwiYXVkIjoiOXE2aHphcTZjZmI0cWRia3UxOXQxY29xZTQydTQzYnYiLCJleHAiOjE1NTk3MDY5MTcsIm5iZiI6MTU1OTY5OTcxN30.IGv0eKhIkpGHFc3YwWZiagF4UwlLEome9b76MCobWN8");
        headres.put("Content-Type", "application/x-www-form-urlencoded");
        Map<String, Object> params = new HashMap<>();
        params.put("userAccount", "yimcarson@qq.com");
        params.put("sendType", 21);
        String result = HttpUtils.post(uri, headres, params);
        System.out.println(result);
    }

    @Test
    public void testPostJson() {
        String uri = "http://47.52.46.240/api/v1/sendVerifyCode";
        Map<String, String> headres = new HashMap<>();
        headres.put("Content-Type", "application/json");
        String body = "{\"userAccount\": \"yimcarson@qq.com\", \"sendType\": 21}";
        String result = HttpUtils.post(uri, headres, body);
        System.out.println(result);
    }

    @Test
    public void test() {
        for (int i = 0; i < 256; i++) {
            System.out.printf("%s\t%s\n", Integer.toHexString(i), (char) i);
        }
    }
}