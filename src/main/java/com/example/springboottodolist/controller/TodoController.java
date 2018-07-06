package com.example.springboottodolist.controller;

import com.example.springboottodolist.domain.Response;
import com.example.springboottodolist.domain.Todo;
import com.example.springboottodolist.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TodoController {

    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/todos")
    public ResponseEntity<Response> getTodos() {
        List<Todo> todos = todoService.findAll();
        return new ResponseEntity<>(new Response<>(todos), HttpStatus.OK);
    }

    @GetMapping("/todos/{todoId}")
    public ResponseEntity<Response> getTodoById(@PathVariable Long todoId) {
        Todo findTodo = todoService.findById(todoId);
        return new ResponseEntity<>(new Response<>(findTodo), HttpStatus.OK);
    }

    @PostMapping("/todos")
    public ResponseEntity<Response> addTodo(@RequestBody final Todo newTodo) {
        Todo savedTodo = todoService.save(newTodo);
        return new ResponseEntity<>(new Response<>(savedTodo), HttpStatus.CREATED);
    }

    @PutMapping("/todos/{todoId}")
    public ResponseEntity<Response> modifyTodo(@PathVariable Long todoId, @RequestBody final Todo modifiedTodo) {
        Todo findTodo = todoService.findById(todoId);
        findTodo.setContext(modifiedTodo.getContext());
        Todo newSavedTodo = todoService.save(findTodo);
        return new ResponseEntity<>(new Response<>(newSavedTodo), HttpStatus.OK);
    }

    @DeleteMapping("/todos/{todoId}")
    public ResponseEntity<Response> deleteTodoById(@PathVariable Long todoId) {
        todoService.deleteById(todoId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
