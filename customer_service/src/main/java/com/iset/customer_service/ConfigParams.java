package com.iset.customer_service;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "global.params")
public record ConfigParams(int p1, int p2) {
}