package com.quizapp.quizapp.entity;

import com.quizapp.quizapp.enums.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "Questions")
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long id;

    @Getter
    @Setter
    private String question;

    @Setter
    private String answers;

    @Getter
    @Setter
    private String correctAnswer;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    public String[] getAnswers() {
        return answers.split(";");
    }

}
