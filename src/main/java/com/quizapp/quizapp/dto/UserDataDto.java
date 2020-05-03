package com.quizapp.quizapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class UserDataDto {

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private int finishedTests;

    @Getter
    @Setter
    private double avgScore;

    @Getter
    @Setter
    private int score;

    @Getter
    @Setter
    private int rank;
}

