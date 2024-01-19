package com.example.bmeter.starter;

import java.util.ArrayList;
import java.util.List;

public class MissingRequiredPropertyException extends RuntimeException{

    private List<String> missingProperties = new ArrayList<>();

    public void addMissingProperty(String property){
        this.missingProperties.add(property);
    }

    public MissingRequiredPropertyException(String message) {
        super(message);
    }
}
