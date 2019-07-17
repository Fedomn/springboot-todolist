package com.example.springboottodolist.jooqtest.model;

import com.example.springboottodolist.jooqtest.audit.Auditable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Todo extends Auditable {

  private Long id;
  private String context;
}
