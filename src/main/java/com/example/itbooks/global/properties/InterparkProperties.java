package com.example.itbooks.global.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "interpark")
@Data
public class InterparkProperties {
    private String key;
}
