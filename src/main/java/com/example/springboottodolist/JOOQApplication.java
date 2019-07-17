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

@SpringBootApplication
public class JOOQApplication implements CommandLineRunner {

  private final DSLContext context;

  @Autowired
  public JOOQApplication(DSLContext context) {
    this.context = context;
  }

  public static void main(String[] args) {
    SpringApplication.run(JOOQApplication.class, args);
  }

  @Override
  public void run(String... args) {
    //    testNormalOperation();
    //    testVisitListener();
    testRecordListener();
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

  private void clear() {
    context.delete(TODOS).execute();
    context.delete(USERS).execute();
  }
}
