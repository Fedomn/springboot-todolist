package com.example.springboottodolist;

import com.example.springboottodolist.domain.Todo;
import com.example.springboottodolist.domain.User;
import com.example.springboottodolist.service.TodoService;
import com.example.springboottodolist.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner setUpDb(UserService userService, TodoService todoService) {
        return (args) -> {
            userService.deleteAll();
            User user1 = new User(1, "user1");
            userService.save(new User(2, "user2"));

            todoService.deleteAll();
            Todo todo1 = new Todo(1, "todo1");
            todo1.setUser(user1);
            Todo todo2 = new Todo(2, "todo2");
            todo2.setUser(user1);
            Todo todo3 = new Todo(3, "todo3");
            todo3.setUser(user1);

            user1.setTodos(Arrays.asList(todo1, todo2, todo3));
            userService.save(user1);

            List<User> users = userService.findAll();
            users.forEach(user -> log.info("user: {}, todos: {}", user, user.getTodos()));
        };
    }

}
