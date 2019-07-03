package com.example.springboottodolist.config;

import org.jooq.conf.Settings;
import org.jooq.conf.StatementType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JOOQConfiguration {

  @Bean
  public Settings jooqSettings() {
    Settings settings = new Settings();
    settings.setStatementType(StatementType.STATIC_STATEMENT);
    return settings;
  }
}