package com.quizapp.quizapp.respository;

import com.quizapp.quizapp.entity.Question;
import com.quizapp.quizapp.enums.Difficulty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    List<Question> findByDifficulty(Difficulty difficulty);

    List<Question> findAll();

}

