package com.example.springboottodolist.service;

import com.example.springboottodolist.domain.User;
import java.util.List;

public interface UserService {

  User findById(Long id);

  List<User> findAll();

  User save(User translator);

  void deleteById(Long id);

  void deleteAllInBatch();
}
