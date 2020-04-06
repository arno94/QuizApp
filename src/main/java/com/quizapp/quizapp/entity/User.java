package com.quizapp.quizapp.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

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

    @Getter
    private String roles;

    @Getter
    private Date date;

    public User() {}

    public User(final String username, final String password, final String roles, final Date date) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.date = date;
    }
}
