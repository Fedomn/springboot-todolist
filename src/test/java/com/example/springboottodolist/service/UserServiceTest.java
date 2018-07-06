package com.example.springboottodolist.service;

import com.example.springboottodolist.exception.UserNotFoundException;
import com.example.springboottodolist.repository.UserRepository;
import com.example.springboottodolist.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test(expected = UserNotFoundException.class)
    public void shouldThrowUserNotFoundException() {
        Long id = 999L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());
        userService.findById(id);
    }
}