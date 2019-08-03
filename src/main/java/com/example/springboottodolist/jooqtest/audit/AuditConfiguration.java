package com.example.springboottodolist.jooqtest.audit;

import org.jooq.RecordListenerProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuditConfiguration {

  //  @Bean
  //  public VisitListenerProvider auditVisitListenerProvider() {
  //    return AuditVisitListener::new;
  //  }

  @Bean
  public RecordListenerProvider auditVisitListenerProvider(
      AuditRecordListener auditRecordListener) {
    return () -> auditRecordListener;
  }
}
