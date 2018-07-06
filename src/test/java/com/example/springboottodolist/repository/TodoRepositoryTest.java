package com.example.springboottodolist.repository;

import com.example.springboottodolist.domain.Todo;
import com.example.springboottodolist.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private User savedUser;

    private Todo savedTodo;

    @Before
    public void setUp() {
        savedUser = new User("test1");
        savedTodo = new Todo("todo1", savedUser);
    }

    @Test
    public void shouldSaveTodoWithUserSuccess() {
        // give

        // when
        todoRepository.save(savedTodo);

        testEntityManager.clear();

        // then
        Optional<Todo> findTodo = todoRepository.findById(savedTodo.getId());
        assertThat(findTodo.map(todo -> todo.getUser().getName()).orElse(null)).isEqualTo(savedUser.getName());
    }
}