package com.example.bmeter.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class BMeterPingExecutor {

    private static final Logger logger = LoggerFactory.getLogger(BMeterPingExecutor.class);

    @Value("${bmeter.ping.path:/ping}")
    private String pingPath;
    private ApplicationContextProvider provider;
    private BMeterPingCaller bMeterPingCaller;
    private Environment environment;

    public BMeterPingExecutor(ApplicationContextProvider provider, BMeterPingCaller bMeterPingCaller, Environment environment){
        this.provider = provider;
        this.bMeterPingCaller = bMeterPingCaller;
        this.environment = environment;
    }

    public List<ExternalPingResponse> doPingHealth(){
        List<ExternalPingResponse> responses = new ArrayList<>();
        Map<String,Object> beans = this.provider.getApplicationContext().getBeansWithAnnotation(BMeterPing.class);
        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            BMeterPing annotation = this.provider.getApplicationContext().findAnnotationOnBean(entry.getKey(), BMeterPing.class);
            String serviceName = getServiceName(annotation.name());

            String servicePingUrl = pingPath;
            if (!annotation.url().equals("")){
                String serviceUrl = getServiceName(annotation.url());
                servicePingUrl = serviceUrl.concat(pingPath);
            }

            Object bean = entry.getValue();
            if (bean instanceof RestTemplate){
                responses.addAll(processForRestTemplate(bean, serviceName, servicePingUrl));
            }else if( bean instanceof WebClient){
                responses.addAll(processForWebClient(bean, serviceName, servicePingUrl));
            }
        }
        return responses;
    }

    private List<ExternalPingResponse> processForRestTemplate(Object bean, String serviceName, String servicePingUrl){
        List<ExternalPingResponse> responses = new ArrayList<>();
        RestTemplate restTemplate = (RestTemplate) bean;
        SimplePingResponse response;
        try {
            response = bMeterPingCaller.executePing(restTemplate, servicePingUrl);
        } catch (RestClientException e) {
            response = new SimplePingResponse("KO", e.getMessage());
        }
        responses.add(new ExternalPingResponse(serviceName, response.status(), response.message()));
        return responses;
    }

    private List<ExternalPingResponse> processForWebClient(Object bean, String serviceName, String servicePingUrl){
        List<ExternalPingResponse> responses = new ArrayList<>();
        WebClient webClient = (WebClient) bean;
        SimplePingResponse response;
        try {
            response = bMeterPingCaller.executePing(webClient, servicePingUrl);
        }catch (Exception e){
            response = new SimplePingResponse("KO", e.getMessage());
        }
        responses.add(new ExternalPingResponse(serviceName, response.status(), response.message()));
        return responses;
    }


    private String getServiceName(String annotationNameField){
        String serviceName = annotationNameField;
        List<String> properties = PlaceHolderHelper.getProperties(annotationNameField);
        if (!properties.isEmpty()){
            for(String property : properties){
                String placeHolderTemplate = "${" + property + "}";
                String propertyValue = environment.getProperty(property, "");
                if (!propertyValue.isEmpty()) {
                    serviceName = PlaceHolderHelper.replaceOnce(annotationNameField, placeHolderTemplate, propertyValue);
                }else{
                    throw new MissingRequiredPropertyException("The following property {"+property+"} is required but could not be resolved");
                }
            }
        }
        return serviceName;
    }


}
