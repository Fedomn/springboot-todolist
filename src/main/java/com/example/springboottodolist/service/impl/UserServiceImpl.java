package com.example.springboottodolist.service.impl;

import com.example.springboottodolist.domain.User;
import com.example.springboottodolist.exception.UserNotFoundException;
import com.example.springboottodolist.repository.UserRepository;
import com.example.springboottodolist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Override
    public Optional<User> updateUserNameById(String name, long id) {
        userRepository.updateUserNameById(name, id);
        return userRepository.findById(id);
    }

    @Override
    public void validateUser(long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
}
