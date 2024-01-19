package com.example.bmeter.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.retry.annotation.EnableRetry;

@Configuration
@EnableRetry
public class BMeterAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(BMeterAutoConfiguration.class);

    private Environment environment;

    public BMeterAutoConfiguration(Environment environment){
        logger.info("New BMeterAutoConfiguration@{} bean created", Integer.toHexString(this.hashCode()));
        this.environment = environment;
    }

    @Bean
    @ConditionalOnProperty(value = "bmeter.ping.enabled", havingValue = "true", matchIfMissing = false)
    public BMeterPingExecutor bMeterPingExecutor(){
        BMeterPingExecutor BMeterPingExecutor = new BMeterPingExecutor(applicationContextProvider(), bMeterPingCaller(), environment);
        logger.info("New BMeterPingExecutor@{} bean created", Integer.toHexString(BMeterPingExecutor.hashCode()));
        return BMeterPingExecutor;
    }


    @Bean
    @ConditionalOnProperty(value = "bmeter.ping.enabled", havingValue = "true", matchIfMissing = false)
    public ApplicationContextProvider applicationContextProvider(){
        ApplicationContextProvider provider = new ApplicationContextProvider();
        logger.info("New ApplicationContextProvider@{} bean created", Integer.toHexString(provider.hashCode()));
        return provider;
    }


    @Bean
    @ConditionalOnProperty(value = "bmeter.ping.enabled", havingValue = "true", matchIfMissing = false)
    public BMeterPingController bMeterPingController(){
        BMeterPingController bMeterPingController = new BMeterPingController(bMeterPingExecutor());
        logger.info("New BMeterPingController@{} bean created", Integer.toHexString(bMeterPingController.hashCode()));
        return bMeterPingController;
    }


    @Bean
    @ConditionalOnProperty(value = "bmeter.ping.enabled", havingValue = "true", matchIfMissing = false)
    public BMeterPingCaller bMeterPingCaller(){
        BMeterPingCaller bMeterPingCaller = new BMeterPingCaller();
        logger.info("New BMeterPingCaller@{} bean created", Integer.toHexString(bMeterPingCaller.hashCode()));
        return bMeterPingCaller;
    }

    @Bean
    @ConditionalOnProperty(value = "bmeter.ping.enabled", havingValue = "true", matchIfMissing = false)
    public RestResponseEntityExceptionHandler exceptionHandler(){
        RestResponseEntityExceptionHandler exceptionHandler = new RestResponseEntityExceptionHandler();
        logger.info("New RestResponseEntityExceptionHandler@{} bean created", Integer.toHexString(exceptionHandler.hashCode()));
        return exceptionHandler;
    }

}
