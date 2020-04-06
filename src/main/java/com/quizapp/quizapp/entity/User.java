package com.quizapp.quizapp.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long id;

    @Getter
    private String username;

    @Getter
    private String password;

    public User() {}

    public User(final String username, final String password) {
        this.username = username;
        this.password = password;
    }
}
