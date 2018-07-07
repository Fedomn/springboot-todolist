package com.example.springboottodolist.controller;

import com.example.springboottodolist.domain.Response;
import com.example.springboottodolist.domain.Todo;
import com.example.springboottodolist.domain.User;
import com.example.springboottodolist.service.TodoService;
import com.example.springboottodolist.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  private final UserService userService;

  private final TodoService todoService;

  @Autowired
  public UserController(UserService userService, TodoService todoService) {
    this.userService = userService;
    this.todoService = todoService;
  }

  @GetMapping("/users")
  public ResponseEntity<Response> getUsers() {
    List<User> users = userService.findAll();
    return new ResponseEntity<>(new Response<>(users), HttpStatus.OK);
  }

  @GetMapping("/users/{userId}")
  public ResponseEntity<Response> getUserById(@PathVariable Long userId) {
    User findUser = userService.findById(userId);
    return new ResponseEntity<>(new Response<>(findUser), HttpStatus.OK);
  }

  @PostMapping("/users")
  private ResponseEntity<Response> saveUser(@RequestBody final User newUser) {
    User savedUser = userService.save(newUser);
    return new ResponseEntity<>(new Response<>(savedUser), HttpStatus.CREATED);
  }

  @PutMapping("/users/{userId}")
  public ResponseEntity<Response> updateById(
      @PathVariable Long userId, @RequestBody final User modifyUser) {
    User findUser = userService.findById(userId);
    findUser.setName(modifyUser.getName());
    User newSavedUser = userService.save(findUser);
    return new ResponseEntity<>(new Response<>(newSavedUser), HttpStatus.OK);
  }

  @DeleteMapping("/users/{userId}")
  public ResponseEntity deleteById(@PathVariable Long userId) {
    userService.deleteById(userId);
    return new ResponseEntity(HttpStatus.OK);
  }

  @GetMapping("/users/{userId}/todos")
  public ResponseEntity<Response> getUserTodos(@PathVariable Long userId) {
    User findUser = userService.findById(userId);
    List<Todo> todos = findUser.getTodos();
    return new ResponseEntity<>(new Response<>(todos), HttpStatus.OK);
  }

  @PostMapping("/users/{userId}/todos")
  public ResponseEntity<Response> saveUserTodos(
      @PathVariable Long userId, @RequestBody final Todo savedTodo) {
    User findUser = userService.findById(userId);
    savedTodo.setUser(findUser);
    todoService.save(savedTodo);
    return new ResponseEntity<>(new Response<>(savedTodo), HttpStatus.CREATED);
  }

  @DeleteMapping("/users/{userId}/todos/{todoId}")
  public ResponseEntity deleteById(@PathVariable Long userId, @PathVariable Long todoId) {
    User findUser = userService.findById(userId);
    Todo findTodo = todoService.findById(todoId);
    findUser.getTodos().remove(findTodo);
    userService.save(findUser);
    return new ResponseEntity(HttpStatus.OK);
  }
}
