package com.quizapp.quizapp.util;

import com.quizapp.quizapp.entity.Question;
import com.quizapp.quizapp.enums.Difficulty;
import com.quizapp.quizapp.respository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuizGenerator {

    @Autowired
    private QuestionRepository questionRepository;

    public List<Question> generateQuiz(final int questionNumber, final Difficulty difficulty) {
        final List<Question> questionList = new ArrayList<>();
        final List<Question> fullQuestionList;

        if (difficulty.equals(Difficulty.ALL)) {
            fullQuestionList = questionRepository.findAll();
        } else {
            fullQuestionList = questionRepository.findByDifficulty(difficulty);
        }

        final Random random = new Random();
        while (questionList.size() < questionNumber) {
            final int questionId = random.nextInt(fullQuestionList.size());
            questionList.add(fullQuestionList.remove(questionId));
        }
        return questionList;
    }

}
