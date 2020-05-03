package com.quizapp.quizapp.enums;

import lombok.Getter;

public enum Difficulty {

    EASY("easy"),
    MEDIUM("medium"),
    HARD("hard"),
    ALL("all");

    @Getter
    private String realName;

    Difficulty(String realName) {
        this.realName = realName;
    }

}
