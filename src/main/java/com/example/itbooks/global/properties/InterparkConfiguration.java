package com.example.itbooks.global.properties;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Configuration
public class InterparkConfiguration {

    @Bean
    public WebClient interparkWebClient() {
        return WebClient.builder()
                .baseUrl("http://book.interpark.com/api").build();
    }
}
