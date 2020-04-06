package com.quizapp.quizapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class UserDto {

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String username;

}

