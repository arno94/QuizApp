package com.quizapp.quizapp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Statistics {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long id;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn
    private User user;

    @Getter
    @Setter
    private int answeredQuestions;

    @Getter
    @Setter
    private int correctAnswers;

    @Getter
    @Setter
    private int finishedTest;

}
