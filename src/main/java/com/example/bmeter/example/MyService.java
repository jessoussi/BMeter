package com.example.bmeter.example;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MyService {

    private RestTemplate restTemplate;

    public MyService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public void doRun(){
        System.out.println(restTemplate.getForObject("/api/test", String.class));
    }
}
