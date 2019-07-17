package com.example.springboottodolist.jooqtest.audit;

import static com.example.springboottodolist.jooq.tables.Operator.OPERATOR;
import static java.util.Objects.nonNull;

import com.example.springboottodolist.jooq.tables.records.OperatorRecord;
import java.time.LocalDateTime;
import org.jooq.DSLContext;
import org.jooq.RecordContext;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultRecordListener;

public class RecordListener extends DefaultRecordListener {

  public static final String name = "modify-name-2";
  public static final String CREATED_DATE = "created_date";
  public static final String CREATED_BY = "created_by";
  public static final String ID = "1234";
  private static final String LAST_MODIFIED_DATE = "last_modified_date";

  @Override
  public void insertStart(RecordContext ctx) {
    if (nonNull(ctx.recordType().field(CREATED_DATE))) {
      ctx.record().set(DSL.field(CREATED_DATE), LocalDateTime.now());

      DSLContext dslContext = ctx.configuration().dsl();
      Result<OperatorRecord> operatorRecords =
          dslContext.selectFrom(OPERATOR).where(OPERATOR.ID.eq(ID)).fetch();
      if (operatorRecords.size() > 0) {
        OperatorRecord operatorRecord = operatorRecords.get(0);
        // diff name
        if (!operatorRecord.getName().equals(name)) {
          dslContext.update(OPERATOR).set(OPERATOR.NAME, name).where(OPERATOR.ID.eq(ID)).execute();
        }
      } else {
        dslContext
            .insertInto(OPERATOR)
            .set(OPERATOR.ID, ID)
            .set(OPERATOR.NAME, name)
            .set(OPERATOR.ROLE, "role")
            .execute();
      }

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
