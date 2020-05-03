package com.quizapp.quizapp.respository;

import com.quizapp.quizapp.entity.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatisticsRepository extends JpaRepository<Statistics, Integer> {

    Optional<Statistics> findByUserId(Long username);

    int countByScoreGreaterThan(int score);

}

