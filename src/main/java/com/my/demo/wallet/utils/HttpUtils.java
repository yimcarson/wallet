package com.my.demo.wallet.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class HttpUtils {
    private static final String CHARSET = "UTF-8";

    private static final RequestConfig requestConfig = RequestConfig.custom()
            .setConnectTimeout(35000)
            .setConnectionRequestTimeout(35000)
            .setSocketTimeout(60000)
            .build();

    private static CloseableHttpClient httpClient;

    private static CloseableHttpResponse response;

    private static HttpRequestBase request;

    public static String get(String url) {
        return get(url, new HashMap<>());
    }

    public static String get(String url, Map<String, String> headers) {
        String result = "";
        try {
            createClient(url);
            request = new HttpGet(url);
            setHeader(headers);
            setConfig();
            result = execute();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return result;
    }

    public static String post(String url, Map<String, String> headers, Map<String, Object> params) {
        String result = "";
        try {
            createClient(url);
            request = new HttpPost(url);
            setConfig();
            setHeader(headers);
            if (null != params && params.size() > 0) {
                List<NameValuePair> nameValuePairList = new ArrayList<>();
                Set<Map.Entry<String, Object>> entries = params.entrySet();
                for (Map.Entry<String, Object> mapEntry : entries) {
                    nameValuePairList.add(new BasicNameValuePair(mapEntry.getKey(), mapEntry.getValue().toString()));
                }
                try {
                    HttpPost httpPost = (HttpPost) request;
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairList, CHARSET));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            result = execute();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return result;
    }

    public static String post(String url, Map<String, String> headers, String body) {
        String result = "";
        try {
            createClient(url);
            request = new HttpPost(url);
            setConfig();
            setHeader(headers);
            if (StringUtils.isNotBlank(body)) {
                StringEntity entity = new StringEntity(body, CHARSET);
                HttpPost httpPost = (HttpPost) request;
                httpPost.setEntity(entity);
            }
            result = execute();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return result;
    }

    private static void createClient(String url) {
        if (url.startsWith("https")) {
            httpClient = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
        } else {
            httpClient = HttpClients.createDefault();
        }
    }

    private static void setHeader(Map<String, String> headers) {
        if (null != headers && headers.size() > 0) {
            Set<String> names = headers.keySet();
            names.forEach(name -> {
                request.setHeader(name, headers.get(name));
            });
        }
    }
    
    private static void setConfig() {
        request.setConfig(requestConfig);
    }
    
    private static String execute() throws IOException {
        response = httpClient.execute(request);
        HttpEntity entity = response.getEntity();
        return EntityUtils.toString(entity);
    }

    private static void close() {
        if (null != response) {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (null != httpClient) {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
