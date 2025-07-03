package com.yummy.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class HttpClientTest {

    @Test
    public void testGet() throws Exception {
        // 1. create httpclient object
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 2. create request object: GET
        HttpGet httpGet = new HttpGet("http://localhost:4041/user/shop/status");
        // 3. send request and receive the response
        CloseableHttpResponse response = httpClient.execute(httpGet);
        // 4. receive status code returned from server
        // 4.1 return status code
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("Status code returned from server: " + statusCode);
        // 4.2 return data
        HttpEntity entity = response.getEntity();
        String body = EntityUtils.toString(entity);
        System.out.println("Data/Body returned from server: " + body);
        // 5. close the resource
        response.close();
        httpClient.close();
    }

    @Test
    public void testPost() throws Exception {
        // 1. create httpclient object
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 2. create request object: POST
        // 2.1
        HttpPost httpPost = new HttpPost("http://localhost:4041/admin/employee/login");
        // 2.2 request body: json, {"username": '', "password": '', ... }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", "admin");
        jsonObject.put("password", "123456");
        StringEntity stringEntity = new StringEntity(jsonObject.toString());
        stringEntity.setContentEncoding("utf-8");
        stringEntity.setContentType("application/json");
        httpPost.setEntity(stringEntity);
        // 3. send request
        CloseableHttpResponse response = httpClient.execute(httpPost);
        // 4. return response
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("status code: " + statusCode);
        HttpEntity entity = response.getEntity();
        String body = EntityUtils.toString(entity);
        System.out.println("response data: " + body);
        // 5. close the resource
        response.close();
        httpClient.close();
    }
}
