package com.example.demomockito.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "url")
public class UrlProperties {
    String githubUrl;
}
