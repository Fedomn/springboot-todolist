package com.example.springboottodolist.controller;

import com.example.springboottodolist.exception.RestError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class ErrorHandlerController {

    @RequestMapping(value = "/404", method = GET)
    public ResponseEntity<RestError> handleNotFoundError() {
        return new ResponseEntity<>(new RestError(NOT_FOUND, "not found"), NOT_FOUND);
    }

}
