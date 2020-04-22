package com.quizapp.quizapp.dto;

import com.quizapp.quizapp.entity.Question;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class QuizDto {

    @Getter
    @Setter
    private Question question;

    @Getter
    @Setter
    private String answer;

    public QuizDto(Question question) {
        this.question = question;
    }

    public boolean isCorrectAnswer() {
        return question.getCorrectAnswer().equals(answer);
    }
}

