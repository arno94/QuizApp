package com.quizapp.quizapp.respository;

import com.quizapp.quizapp.entity.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StatisticsRepository extends JpaRepository<Statistics, Integer> {

    Optional<Statistics> findByUserId(Long username);

    int countByScoreGreaterThan(int score);

    @Query("select s from Statistics s order by s.score DESC")
    List<Statistics> findAll();

}

