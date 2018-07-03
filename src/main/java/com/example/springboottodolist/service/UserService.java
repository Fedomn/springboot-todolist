package com.example.springboottodolist.service;

import com.example.springboottodolist.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findById(long id);

    List<User> findAll();

    User save(User translator);

    void deleteById(long id);

    void deleteAll();

    Optional<User> updateUserNameById(String name, long id);

    void validateUser(long userId);
}
