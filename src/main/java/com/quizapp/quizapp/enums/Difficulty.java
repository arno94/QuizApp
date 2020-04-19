package com.quizapp.quizapp.enums;

import lombok.Getter;

public enum Difficulty {

    EASY("könnyű"),
    MEDIUM("közepes"),
    HARD("nehéz"),
    ALL("összes");

    @Getter
    private String realName;

    Difficulty(String realName) {
        this.realName = realName;
    }

}
