package com.example.bmeter.example;

import com.example.bmeter.starter.BMeterPing;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class MyConfiguration {

    @Bean
    @BMeterPing(name = "${myservice.name}", url = "${myservice.url}")
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("https://myserver.free.beeceptor.com"));
        return restTemplate;
    }

    @Bean
    @BMeterPing(name = "myservice2", url = "https://myserver.free.beeceptor.com")
    public WebClient webClient(){
        WebClient webClient = WebClient.create("https://myserver.free.beeceptor.com");
        return webClient;
    }
}
