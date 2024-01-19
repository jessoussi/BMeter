package com.example.bmeter.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
public class ExampleMain {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(ExampleMain.class, args);
        MyService service = (MyService) context.getBean("myService");
        //service.doRun();
    }
}
