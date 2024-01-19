package com.example.bmeter.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@ConditionalOnExpression("${bmeter.ping.enabled:false}")
public class BMeterPingController {

    private static final Logger logger = LoggerFactory.getLogger(BMeterPingController.class);
    private BMeterPingExecutor bMeterPingExecutor;

    public BMeterPingController(BMeterPingExecutor bMeterPingExecutor){
        this.bMeterPingExecutor = bMeterPingExecutor;
    }

    @GetMapping("${bmeter.ping.path:/ping}")
    public ResponseEntity<GlobalPingResponse> doPing(){
        List<ExternalPingResponse> results = this.bMeterPingExecutor.doPingHealth();
        String gloablStatus = "OK";
        String gloablMessage = "Service UP";
        for(ExternalPingResponse response : results){
            if (!response.status().equalsIgnoreCase("OK")){
                gloablStatus = "KO";
                gloablMessage = "One or more external service(s) are inreachable!!!";
                break;
            }
        }
        GlobalPingResponse globalPingResponse = new GlobalPingResponse(gloablStatus, gloablMessage, results);
        return new ResponseEntity<>(globalPingResponse, HttpStatus.OK);
    }

}
