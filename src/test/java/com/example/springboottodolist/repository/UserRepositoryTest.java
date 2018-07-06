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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    private User savedUser;

    private Todo savedTodo;

    private List<Todo> savedTodoList = new ArrayList<>();

    @Before
    public void setUp() {
        savedUser = new User("test1");
        savedTodo = new Todo("todo1", savedUser);
        savedTodoList.add(savedTodo);
        savedUser.setTodos(savedTodoList);
    }

    @Test
    public void testUserCompare() {
        User cmpUser = new User("test1");
        Todo cmpTodo = new Todo("todo1", cmpUser);
        cmpUser.setTodos(Collections.singletonList(cmpTodo));
        assertThat(savedUser).isEqualTo(cmpUser);
    }

    @Test
    public void shouldSaveUserWithTodosSuccess() {
        // give
        User savedUser = new User("user1");
        Todo savedTodo = new Todo("todo1", savedUser);
        savedUser.setTodos(Collections.singletonList(savedTodo));

        // when
        userRepository.saveAndFlush(savedUser);

        entityManager.clear();
        Optional<User> findUser = userRepository.findById(savedTodo.getId());

        // then
        assertThat(findUser.isPresent()).isTrue();
        assertThat(findUser.map(user -> user.getTodos().get(0)).orElse(null)).isEqualTo(savedTodo);
    }

    @Test
    public void shouldModifyUserSuccess() {
        // give
        entityManager.persistAndFlush(savedUser);

        // when
        String modifyName = "modifyName";
        userRepository.updateUserNameById(modifyName, savedUser.getId());

        entityManager.clear();
        Optional<User> findUser = userRepository.findById(savedUser.getId());

        // then
        assertThat(findUser.map(User::getName).orElse(null)).isEqualTo(modifyName);
    }

    @Test
    public void shouldModifyUserTodosSuccess() {
        // give
        entityManager.persistAndFlush(savedUser);

        // when
        savedUser.getTodos().remove(savedTodo);
        // savedUser与session里的user比较
        userRepository.saveAndFlush(savedUser);

        Todo newTodo = new Todo("new todo", savedUser);
        savedUser.getTodos().add(newTodo);
        userRepository.saveAndFlush(savedUser);

        entityManager.clear();
        Optional<User> findUser = userRepository.findById(savedUser.getId());

        // then
        assertThat(findUser.map(user -> user.getTodos().get(0).getContext()).orElse(null)).isEqualTo(newTodo.getContext());
    }

    @Test
    public void shoudAddUserTodosSuccess() {
        // give
        entityManager.persistAndFlush(savedUser);

        // when
        Todo newTodo = new Todo("new todo", savedUser);
        savedUser.getTodos().add(newTodo);
        userRepository.saveAndFlush(savedUser);

        entityManager.clear();
        Optional<User> findUser = userRepository.findById(savedUser.getId());

        // then
        assertThat(findUser.map(user -> user.getTodos().size()).orElse(0)).isEqualTo(2);
    }
}