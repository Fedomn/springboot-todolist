package com.example.springboottodolist.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "todos")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "context")
    private String context;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "none"))
    private User user;

    public Todo() {
    }

    public Todo(String context) {
        this.context = context;
    }

    public Todo(Long id, String context) {
        this.id = id;
        this.context = context;
    }

    public Todo(String context, User user) {
        this.context = context;
        this.user = user;
    }

    public Todo(Long id, String context, User user) {
        this.id = id;
        this.context = context;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Todo)) return false;
        Todo todo = (Todo) obj;
        return Objects.equals(todo.id, id) && Objects.equals(todo.context, context);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result += id != null ? id.hashCode() : 0;
        result += context != null ? context.hashCode() : 0;
        return result;
    }
}
