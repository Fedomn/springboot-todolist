package com.example.springboottodolist.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "todos")
public class Todo {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "context")
    private String context;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "todo_user_id")
    private User user;

    public Todo() {
    }

    public Todo(String context) {
        this.context = context;
    }

    public Todo(long id, String context) {
        this.id = id;
        this.context = context;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", context='" + context + '\'' +
                '}';
    }
}
