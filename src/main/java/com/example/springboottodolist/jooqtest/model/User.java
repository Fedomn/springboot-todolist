package com.example.springboottodolist.jooqtest.model;

import com.example.springboottodolist.jooqtest.audit.Auditable;

public class User extends Auditable {

  private Long id;
  private String name;
}
