package org.parths.orderservice.configuration;

import org.parths.productservicev2.client.ProductServiceClient;
import org.parths.userservice.client.UserServiceClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfiguration {

    @Value("${product.service.url}")
    private String productServiceUrl;

    @Value("${user.service.url}")
    private String userServiceUrl;

    @Bean
    public ProductServiceClient getProductServiceClient(){
        return new ProductServiceClient(getWebClientForBaseUrl(productServiceUrl));
    }

    @Bean
    public UserServiceClient getUserServiceClient(){
        return new UserServiceClient(getWebClientForBaseUrl(userServiceUrl));
    }

    private static WebClient getWebClientForBaseUrl(String baseUrl){
        return WebClient.builder().baseUrl(baseUrl).build();
    }
}
