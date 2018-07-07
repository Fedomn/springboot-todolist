package com.example.springboottodolist.service;

import static org.mockito.Mockito.when;

import com.example.springboottodolist.exception.UserNotFoundException;
import com.example.springboottodolist.repository.UserRepository;
import com.example.springboottodolist.service.impl.UserServiceImpl;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

  @Mock private UserRepository userRepository;

  @InjectMocks private UserServiceImpl userService;

  @Test(expected = UserNotFoundException.class)
  public void shouldThrowUserNotFoundException() {
    Long id = 999L;
    when(userRepository.findById(id)).thenReturn(Optional.empty());
    userService.findById(id);
  }
}
