package com.example.bmeter.starter;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention( RetentionPolicy.RUNTIME )
public @interface BMeterPing {

    String name();

    String url() default "";
}
