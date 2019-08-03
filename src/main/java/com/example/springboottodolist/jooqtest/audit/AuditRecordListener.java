package com.example.springboottodolist.jooqtest.audit;

import static java.util.Objects.nonNull;

import java.time.LocalDateTime;
import org.jooq.RecordContext;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultRecordListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuditRecordListener extends DefaultRecordListener {

  private static final String name = "modify-name-2";
  private static final String ID = "1234";
  private static final String CREATED_DATE = "created_date";
  private static final String CREATED_BY = "created_by";
  private static final String LAST_MODIFIED_DATE = "last_modified_date";
  private final OperatorUpdatedPublisher operatorUpdatedPublisher;

  @Autowired
  public AuditRecordListener(OperatorUpdatedPublisher operatorUpdatedPublisher) {
    this.operatorUpdatedPublisher = operatorUpdatedPublisher;
  }

  @Override
  public void insertStart(RecordContext ctx) {
    if (nonNull(ctx.recordType().field(CREATED_DATE))) {
      ctx.record().set(DSL.field(CREATED_DATE), LocalDateTime.now());

      operatorUpdatedPublisher.updateOperator(ID, name, "role");

      ctx.record().set(DSL.field(CREATED_BY), ID);
    }
  }

  @Override
  public void updateStart(RecordContext ctx) {
    if (nonNull(ctx.recordType().field(LAST_MODIFIED_DATE))) {
      ctx.record().changed(CREATED_DATE, false);
      ctx.record().set(DSL.field(LAST_MODIFIED_DATE), LocalDateTime.now());
    }
  }
}
