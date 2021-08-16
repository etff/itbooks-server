package com.example.itbooks.global.properties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableConfigurationProperties({
        InterparkProperties.class,
        JwtProperties.class
})
@PropertySource({
        "classpath:properties/interpark.properties",
        "classpath:properties/jwt.properties"
})
public class PropertiesConfiguration {
}
