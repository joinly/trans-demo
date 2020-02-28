package com.gd.trans.example.demo.utils;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


public class HttpUtil {

    /**
     *  json请求
     * @param url
     * @param json
     * @return
     */
    public static ResponseEntity postJson(String url, String json) {
        try {
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
            RestTemplate restTemplate = restTemplateBuilder.build();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            //headers.add("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1ODUxOTM2MzEsInVzZXJJZCI6Ijc2IiwidXNlcm5hbWUiOiIxNTkxNjI2NDYxNiJ9.b5rKwMgu8T-L6uFPFnBAYrnIuSgFAk45DrHw7Nh_wjo");
            HttpEntity<String> entity = new HttpEntity<String>(json, headers);
            return restTemplate.postForEntity(url, entity, String.class);
        } catch (HttpClientErrorException e) {
            throw e;
        }
    }

}
