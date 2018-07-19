package com.example.springboottodolist.config;

import com.example.springboottodolist.property.FileStorageProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({FileStorageProperties.class})
public class PropertyConfig {}
