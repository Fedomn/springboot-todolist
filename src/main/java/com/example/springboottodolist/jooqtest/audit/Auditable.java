package com.example.springboottodolist.jooqtest.audit;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Auditable {

  private LocalDateTime createdDate;

  private LocalDateTime lastModifiedDate;

  private String createdBy;

  private String lastModifiedBy;
}
