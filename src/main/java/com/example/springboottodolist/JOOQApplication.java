package com.example.springboottodolist;

import static com.example.springboottodolist.jooq.tables.Todos.TODOS;
import static com.example.springboottodolist.jooq.tables.Users.USERS;

import com.example.springboottodolist.jooq.tables.records.UsersRecord;
import com.example.springboottodolist.jooqtest.model.User;
import java.util.List;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
@EnableTransactionManagement
public class JOOQApplication implements CommandLineRunner {

  private final DSLContext context;

  @Autowired
  public JOOQApplication(DSLContext context) {
    this.context = context;
    this.context.settings().setExecuteWithOptimisticLocking(true);
    this.context.settings().setExecuteWithOptimisticLockingExcludeUnversioned(true);
  }

  public static void main(String[] args) {
    SpringApplication.run(JOOQApplication.class, args);
  }

  @Override
  @Transactional
  public void run(String... args) {
    //    testNormalOperation();
    //    testVisitListener();
    //    testRecordListener();
    //    testBatchStoreCauseExceptionInRecordListener();
    //    testOptimisticLocking();
  }

  private void testOptimisticLocking() {
    f1();
    f2();
  }

  private void f1() {
    UsersRecord u1 = context.selectFrom(USERS).where(USERS.ID.eq(17L)).fetchOne();
    new Thread(
            () -> {
              u1.setName("u1");
              u1.store();
            })
        .start();
  }

  private void f2() {
    UsersRecord u2 = context.selectFrom(USERS).where(USERS.ID.eq(17L)).fetchOne();
    new Thread(
            () -> {
              u2.setName("u2");
              u2.store();
            })
        .start();
  }

  private void testNormalOperation() {
    context.insertInto(USERS, USERS.NAME).values("name").execute();

    System.out.println(
        context.insertInto(USERS, USERS.NAME).values("  `? / \n \\ ' 'name' ").getSQL());

    context.update(USERS).set(USERS.NAME, "changed-name").where(USERS.NAME.eq("name")).execute();

    List<User> users = context.selectFrom(USERS).fetch().into(User.class);
    for (User user : users) {
      System.out.println(user);
    }
  }

  private void testVisitListener() {
    clear();

    context
        .insertInto(USERS, USERS.ID, USERS.NAME)
        .values(12L, "name")
        .onDuplicateKeyUpdate()
        .set(USERS.NAME, "name2")
        .execute();

    User user = context.selectFrom(USERS).where(USERS.ID.eq(12L)).fetchOne().into(User.class);
    System.out.println(user);

    context.update(USERS).set(USERS.NAME, "update-name").execute();

    UsersRecord usersRecord = context.newRecord(USERS);
    usersRecord.store();

    context.insertInto(USERS, USERS.ID, USERS.NAME).values(11L, "name").execute();
  }

  private void testRecordListener() {
    UsersRecord usersRecord = context.fetchOne(USERS);
    //    UsersRecord usersRecord = context.newRecord(USERS);

    User pojo = User.builder().id(12L).name("nam45").build();
    if (!usersRecord.into(User.class).equals(pojo)) {
      usersRecord.from(pojo);
      usersRecord.store();
    }
  }

  // BatchUpdateException: Can not issue SELECT via executeUpdate() or executeLargeUpdate().
  private void testBatchStoreCauseExceptionInRecordListener() {
    UsersRecord usersRecord1 = context.newRecord(USERS);
    usersRecord1.setId(1L);
    usersRecord1.setName("");

    UsersRecord usersRecord2 = context.newRecord(USERS);
    usersRecord2.setId(2L);
    usersRecord2.setName("");

    context.batchStore(usersRecord1, usersRecord2).execute();
  }

  private void clear() {
    context.delete(TODOS).execute();
    context.delete(USERS).execute();
  }
}
