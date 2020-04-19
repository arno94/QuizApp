package com.quizapp.quizapp.respository;

import com.quizapp.quizapp.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
}

