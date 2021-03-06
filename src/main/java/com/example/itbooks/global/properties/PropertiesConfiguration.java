package com.example.itbooks.global.properties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@EnableConfigurationProperties({
        InterparkProperties.class,
        JwtProperties.class
})
@PropertySources({
        @PropertySource("classpath:properties/interpark.properties"),
        @PropertySource("classpath:properties/jwt.properties")
})
public class PropertiesConfiguration {
}
