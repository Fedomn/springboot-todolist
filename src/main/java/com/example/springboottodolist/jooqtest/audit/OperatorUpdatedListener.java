package com.example.springboottodolist.jooqtest.audit;

import static com.example.springboottodolist.jooq.tables.Operator.OPERATOR;

import com.example.springboottodolist.jooq.tables.records.OperatorRecord;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class OperatorUpdatedListener {
  private final DSLContext dslContext;

  @Autowired
  public OperatorUpdatedListener(DSLContext dslContext) {
    this.dslContext = dslContext;
  }

  //  @Async
  @EventListener
  public void on(OperatorUpdatedEvent event) {
    String id = event.getId();
    String name = event.getName();
    String roles = event.getRoles();

    Result<OperatorRecord> operatorRecords =
        dslContext.selectFrom(OPERATOR).where(OPERATOR.ID.eq(id)).fetch();
    if (operatorRecords.size() > 0) {
      OperatorRecord operatorRecord = operatorRecords.get(0);
      // diff name
      if (!operatorRecord.getName().equals(name)) {
        dslContext.update(OPERATOR).set(OPERATOR.NAME, name).where(OPERATOR.ID.eq(id)).execute();
      }
    } else {
      dslContext
          .insertInto(OPERATOR)
          .set(OPERATOR.ID, id)
          .set(OPERATOR.NAME, name)
          .set(OPERATOR.ROLE, roles)
          .execute();
    }
  }
}
