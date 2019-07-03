package com.example.springboottodolist;

import static com.example.springboottodolist.jooq.tables.Users.USERS;

import com.example.springboottodolist.domain.User;
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
    context.insertInto(USERS, USERS.NAME).values("name").execute();

    System.out
        .println(context.insertInto(USERS, USERS.NAME).values("  `? / \n \\ ' 'name' ").getSQL());

    context.update(USERS).set(USERS.NAME, "changed-name").where(USERS.NAME.eq("name")).execute();

    List<User> users = context.selectFrom(USERS).fetch().into(User.class);
    for (User user : users) {
      System.out.println(user);
    }
  }
}
