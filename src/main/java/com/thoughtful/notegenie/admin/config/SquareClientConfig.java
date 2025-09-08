package com.thoughtful.notegenie.admin.config;

import com.squareup.square.SquareClient;
import com.squareup.square.core.Environment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "com.square.api")
public class SquareClientConfig {

    @Value("${client.env}")
    private String clientEnv;

    @Bean
    public SquareClient getSquareClient() {
        return SquareClient.builder().environment(!clientEnv.equals("PRODUCTION")
                ? Environment.SANDBOX
                : Environment.PRODUCTION).build();
    }
}
