package com.example.springboottodolist.jooqtest.audit;

import static com.example.springboottodolist.jooq.tables.Todos.TODOS;
import static java.util.Objects.nonNull;

import java.time.LocalDateTime;
import org.jooq.Field;
import org.jooq.InsertQuery;
import org.jooq.QueryPart;
import org.jooq.UpdateQuery;
import org.jooq.VisitContext;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultVisitListener;
import org.jooq.impl.TableImpl;

public class AuditVisitListener extends DefaultVisitListener {

  private static final String CREATED_DATE = "created_date";
  private static final String LAST_MODIFIED_DATE = "last_modified_date";
  private boolean hasAuditingFields = false;
  private boolean hasSetAuditingDate = false;

  private boolean isFirstInsertQuery = true;
  private InsertQuery firstInsertQueryRef;

  @Override
  public void clauseStart(VisitContext context) {
    if (nonNull(context.renderContext())) {
      QueryPart queryPart = context.queryPart();

      if (hasSetAuditingDate) {
        return;
      }

      if (isFirstInsertQuery && queryPart instanceof InsertQuery) {
        isFirstInsertQuery = false;
        firstInsertQueryRef = (InsertQuery) queryPart;
      }

      if (queryPart instanceof TableImpl) {
        TableImpl table = (TableImpl) queryPart;
        hasAuditingFields =
            nonNull(table.field(CREATED_DATE)) && nonNull(table.field(LAST_MODIFIED_DATE));

        if (hasAuditingFields) {
          firstInsertQueryRef.addValue(renderField(CREATED_DATE), LocalDateTime.now());
          firstInsertQueryRef.addValue(renderField(LAST_MODIFIED_DATE), LocalDateTime.now());
          firstInsertQueryRef.addValueForUpdate(
              renderField(LAST_MODIFIED_DATE), LocalDateTime.now());

          context.context().dsl().insertInto(TODOS, TODOS.ID).values(2L).execute();

          hasSetAuditingDate = true;
        }
      }

      if (queryPart instanceof UpdateQuery) {
        UpdateQuery updateQuery = (UpdateQuery) queryPart;
        updateQuery.addValue(renderField(LAST_MODIFIED_DATE), LocalDateTime.now());
        hasSetAuditingDate = true;
        System.out.println("set hasSetAuditingDate");
      }
    }
  }

  private void setHasAuditingFieldsFlag(QueryPart queryPart) {
    if (queryPart instanceof TableImpl) {
      TableImpl table = (TableImpl) queryPart;
      hasAuditingFields =
          nonNull(table.field(CREATED_DATE)) && nonNull(table.field(LAST_MODIFIED_DATE));
      System.out.println("set flag success");
    }
  }

  //  private void setHasAuditingFieldsFlag(VisitContext context) {
  //    if (hasAuditingFields) {
  //      return;
  //    }
  //
  //    QueryPart queryPart = context.queryPart();
  //    if (queryPart instanceof TableImpl) {
  //      TableImpl table = (TableImpl) queryPart;
  //      hasAuditingFields =
  //          nonNull(table.field(CREATED_DATE)) && nonNull(table.field(LAST_MODIFIED_DATE));
  //      System.out.println("set flag success");
  //    }
  //  }

  //  private Boolean hasAuditingFields(VisitContext context) {
  //    Boolean hasAuditingFields = (Boolean) context.data(HAS_AUDITING_FIELDS);
  //    if (isNull(hasAuditingFields)) {
  //      hasAuditingFields = FALSE;
  //      context.data(HAS_AUDITING_FIELDS, hasAuditingFields);
  //    }
  //    return hasAuditingFields;
  //  }

  //  private void addAuditingDate(QueryPart queryPart) {
  //    if (queryPart instanceof InsertQuery) {
  //      InsertQuery insertQuery = (InsertQuery) queryPart;
  //      insertQuery.addValue(renderField(CREATED_DATE), LocalDateTime.now());
  //      insertQuery.addValue(renderField(LAST_MODIFIED_DATE), LocalDateTime.now());
  //      insertQuery.addValueForUpdate(renderField(LAST_MODIFIED_DATE), LocalDateTime.now());
  //    }
  //
  //    if (queryPart instanceof UpdateQuery) {
  //      UpdateQuery updateQuery = (UpdateQuery) queryPart;
  //      updateQuery.addValue(renderField(LAST_MODIFIED_DATE), LocalDateTime.now());
  //    }
  //  }

  private Field<Object> renderField(String field) {
    return DSL.field(String.format("`%s`", field));
  }
}
