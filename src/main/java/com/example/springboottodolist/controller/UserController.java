package com.example.springboottodolist.controller;

import com.example.springboottodolist.domain.Response;
import com.example.springboottodolist.domain.Todo;
import com.example.springboottodolist.domain.User;
import com.example.springboottodolist.exception.TodoNotFoundException;
import com.example.springboottodolist.service.TodoService;
import com.example.springboottodolist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    private final UserService userService;

    private final TodoService todoService;

    @Autowired
    public UserController(UserService userService, TodoService todoService) {
        this.userService = userService;
        this.todoService = todoService;
    }

    private ResponseEntity<Response> makeUserNotFoundResponse() {
        Response<User> response = new Response<>(null, "user not found", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<Response> makeUserResponse(User user) {
        Response<User> response = new Response<>(user, HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private ResponseEntity<Response> makeUserResponse(User user, HttpStatus status) {
        Response<User> response = new Response<>(user, status);
        return new ResponseEntity<>(response, status);
    }

    @GetMapping("/users")
    public Response<List<User>> getUsers() {
        List<User> users = userService.findAll();
        return new Response<>(users, HttpStatus.OK);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<Response> getUserById(@PathVariable long userId) {
        userService.validateUser(userId);
        Optional<User> user = userService.findById(userId);
        return user.map(this::makeUserResponse).orElseGet(this::makeUserNotFoundResponse);
    }

    @PostMapping("/users")
    private ResponseEntity<Response> saveUser(@RequestBody final User newUser) {
        User user = userService.save(newUser);
        return this.makeUserResponse(user, HttpStatus.CREATED);
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<Response> updateById(@PathVariable long userId, @RequestBody final User modifyUser) {
        Optional<User> user = userService.findById(userId);
        if (user.isPresent()) {
            Optional<User> updateUser = userService.updateUserNameById(modifyUser.getName(), userId);
            return updateUser.map(this::makeUserResponse).orElseGet(this::makeUserNotFoundResponse);
        } else {
            return this.makeUserNotFoundResponse();
        }
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity deleteById(@PathVariable long userId) {
        userService.deleteById(userId);
        return new ResponseEntity(HttpStatus.OK);
    }

    private ResponseEntity<Response> makeTodosResponse(List<Todo> todos, HttpStatus status) {
        Response<List<Todo>> response = new Response<>(todos, status);
        return new ResponseEntity<>(response, status);
    }


    @GetMapping("/users/{userId}/todos")
    public ResponseEntity<Response> getUserTodos(@PathVariable long userId) {
        userService.validateUser(userId);
        Optional<User> user = userService.findById(userId);
        if (user.isPresent()) {
            List<Todo> todos = user.get().getTodos();
            return makeTodosResponse(todos, HttpStatus.OK);
        } else {
            return makeUserNotFoundResponse();
        }
    }

    @PostMapping("/users/{userId}/todos")
    public ResponseEntity<Response> saveUserTodos(@PathVariable long userId, @RequestBody final Todo savedTodo) {
        userService.validateUser(userId);
        Optional<User> user = userService.findById(userId);
        if (user.isPresent()) {
            User userEntity = user.get();
            savedTodo.setUser(userEntity);

            userEntity.getTodos().add(savedTodo);
            userService.save(userEntity);
            return makeTodosResponse(userEntity.getTodos(), HttpStatus.CREATED);
        } else {
            return makeUserNotFoundResponse();
        }
    }

    @DeleteMapping("/users/{userId}/todos/{todoId}")
    public ResponseEntity deleteById(@PathVariable long userId, @PathVariable long todoId) {
        userService.validateUser(userId);

        return todoService.findById(todoId).map(todo -> {
            todoService.delete(todo);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new TodoNotFoundException(todoId));
    }

}
