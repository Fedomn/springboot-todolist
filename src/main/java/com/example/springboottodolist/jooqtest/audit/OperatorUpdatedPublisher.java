package com.example.springboottodolist.jooqtest.audit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class OperatorUpdatedPublisher {
  private final ApplicationEventPublisher applicationEventPublisher;

  @Autowired
  public OperatorUpdatedPublisher(ApplicationEventPublisher applicationEventPublisher) {
    this.applicationEventPublisher = applicationEventPublisher;
  }

  public void updateOperator(String id, String name, String roles) {
    applicationEventPublisher.publishEvent(new OperatorUpdatedEvent(id, name, roles));
  }
}
