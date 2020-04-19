package com.quizapp.quizapp.services;
import com.quizapp.quizapp.dto.UserDto;
import com.quizapp.quizapp.entity.Statistics;
import com.quizapp.quizapp.respository.StatisticsRepository;
import com.quizapp.quizapp.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDataService
{
    @Autowired
    private StatisticsRepository statisticsRepository;

    public double avgScore()
    {
        double avg_score = 0;
        final UserDto userDto = UserDetailsServiceImpl.getLoggedInUserDetails();
        if (statisticsRepository.findByUserId(userDto.getId()).isPresent())
        {
            Statistics s = statisticsRepository.findByUserId(userDto.getId()).get();
            int correct_answers = s.getCorrectAnswers();
            int answered_questions = s.getAnsweredQuestions();
            avg_score = Math.round((((double)correct_answers / answered_questions) * 100.0) * 100.0) / 100.0;
        }
        return avg_score;
    }

    public int finishedTest()
    {
        int finished_test = 0;
        final UserDto userDto = UserDetailsServiceImpl.getLoggedInUserDetails();
        if (statisticsRepository.findByUserId(userDto.getId()).isPresent())
        {
            Statistics s = statisticsRepository.findByUserId(userDto.getId()).get();
            finished_test = s.getFinishedTest();
        }
        return finished_test;
    }
}
