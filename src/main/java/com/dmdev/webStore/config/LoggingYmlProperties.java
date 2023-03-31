package com.dmdev.webStore.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "logging")
public record LoggingYmlProperties(String INFO) {
}
