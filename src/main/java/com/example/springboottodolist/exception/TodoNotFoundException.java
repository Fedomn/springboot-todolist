package com.example.springboottodolist.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "todo not found")
public class TodoNotFoundException extends RuntimeException {
  public TodoNotFoundException(Long id) {
    super("could not found todo " + id);
  }
}
