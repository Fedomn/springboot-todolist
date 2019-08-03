package com.example.springboottodolist.jooqtest.audit;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OperatorUpdatedEvent {
  private String id;
  private String name;
  private String roles;
}
