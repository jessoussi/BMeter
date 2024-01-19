package com.example.bmeter.starter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class BMeterPingCaller {

    @Retryable(retryFor = RestClientException.class, maxAttemptsExpression = "${bmeter.ping.retry.maxAttempts:3}",
            backoff = @Backoff(delayExpression = "${bmeter.ping.retry.delay:3}"))
    public SimplePingResponse executePing(RestTemplate restTemplate, String servicePingUrl) throws RestClientException {
        return restTemplate.getForObject(servicePingUrl, SimplePingResponse.class);
    }

    @Retryable(retryFor = Exception.class, maxAttemptsExpression = "${bmeter.ping.retry.maxAttempts:3}",
            backoff = @Backoff(delayExpression = "${bmeter.ping.retry.delay:3}"))
    public SimplePingResponse executePing(WebClient webClient, String servicePingUrl) throws RuntimeException{
        return webClient.get().uri(servicePingUrl).retrieve().bodyToMono(SimplePingResponse.class).block();
    }
}
