package com.example.springboottodolist.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Todo> todos = new ArrayList<>();

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public User(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Todo> getTodos() {
        return todos;
    }

    public void setTodos(List<Todo> todos) {
        this.todos = todos;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}');
        if (!todos.isEmpty()) {
            for (Todo todo : todos) {
                result.append(String.format(
                        " [%s]", todo
                ));
            }
        }
        return result.toString();
    }
}
