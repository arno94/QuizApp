package com.quizapp.quizapp.controller;
import com.quizapp.quizapp.dto.QuizDto;
import com.quizapp.quizapp.dto.UserDataDto;
import com.quizapp.quizapp.dto.UserDto;
import com.quizapp.quizapp.entity.Question;
import com.quizapp.quizapp.entity.Statistics;
import com.quizapp.quizapp.entity.User;
import com.quizapp.quizapp.enums.Difficulty;
import com.quizapp.quizapp.respository.StatisticsRepository;
import com.quizapp.quizapp.respository.UserRepository;
import com.quizapp.quizapp.security.UserDetailsServiceImpl;
import com.quizapp.quizapp.services.UserDataService;
import com.quizapp.quizapp.util.QuizGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;

@Controller
public class MainController {

    @Autowired
    private UserDataService userDataService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatisticsRepository statisticsRepository;

    @Autowired
    private QuizGenerator quizGenerator;

    private List<QuizDto> quizDtoList;
    private int questionCount;
    private int questionIndex = 0;

    @GetMapping("/")
    public String Main() {
        final UserDto userDto = UserDetailsServiceImpl.getLoggedInUserDetails();
        if (userDto != null) {
            return "redirect:/index";
        }
        return "redirect:/login";
    }

    @GetMapping(value="/login")
    public String Login_Register()
    {
        final UserDto userDto = UserDetailsServiceImpl.getLoggedInUserDetails();
        if (userDto != null) {
            return "redirect:/index";
        }
        return "login";
    }

    @PostMapping(value = "/register")
    public String registration(@RequestParam(value = "username", required = true) final String username,
                               @RequestParam(value = "password", required = true) final String password,
                               Model model) {
        if (userRepository.findByUsername(username).isPresent()) {
            model.addAttribute("exists","Username already exists!");
            return "/login";
        }
        final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        final String pass = passwordEncoder.encode(password);
        User user = new User(username, pass, "ROLE_USER", new Date(), true);
        userRepository.save(user);
        model.addAttribute("registered","Registered successfuly, please log in");

        final Statistics statistics = new Statistics(user.getId(), user, 0, 0, 0, 0);
        statisticsRepository.save(statistics);

        return "/login";
    }

    @GetMapping(value="/index")
    public String Index(Model model)
    {
        final UserDto userDto = UserDetailsServiceImpl.getLoggedInUserDetails();
        final Optional<Statistics> optionalStatistics = statisticsRepository.findByUserId(userDto.getId());
        final UserDataDto userDataDto = new UserDataDto(userDto.getUsername(), userDataService.finishedTest(),
                userDataService.avgScore(), 0, 1);

        if (optionalStatistics.isPresent()) {
            final Statistics statistics = optionalStatistics.get();
            userDataDto.setScore(statistics.getScore());
            userDataDto.setRank(statisticsRepository.countByScoreGreaterThan(statistics.getScore()) + 1);
        }

        model.addAttribute("difficulties", Difficulty.values());
        model.addAttribute("userData", userDataDto);
        return "index";
    }

    @PostMapping(value="/generate_quiz")
    public RedirectView generateQuiz(final RedirectAttributes redirectAttributes,
             @RequestParam Difficulty difficulty, int questionCount) {
        final UserDto userDto = UserDetailsServiceImpl.getLoggedInUserDetails();
        if (userDto != null) {
            final List<Question> questionList = quizGenerator.generateQuiz(questionCount, difficulty);
            quizDtoList = new ArrayList<>();
            questionList.forEach(q -> quizDtoList.add(new QuizDto(q)));
            this.questionCount = questionCount;
            questionIndex = 0;

            return new RedirectView("/quiz");
        }

        return new RedirectView("/index");
    }

    @PostMapping(value = "/quiz")
    public String quiz(Model model, @RequestParam String answer) {
        quizDtoList.get(questionIndex).setAnswer(answer);
        questionIndex++;
        if (questionIndex < questionCount) {
            return "redirect:/quiz";
        }
        return "redirect:/result";
    }

    @GetMapping(value="/quiz")
    public String Quiz(Model model) {
        final UserDto userDto = UserDetailsServiceImpl.getLoggedInUserDetails();
        if (userDto != null) {
            List<String> shuffled_list = Arrays.asList(quizDtoList.get(questionIndex).getQuestion().getAnswers());
            Collections.shuffle(shuffled_list);
            model.addAttribute("questionIndex", questionIndex);
            model.addAttribute("questionCount", questionCount);
            model.addAttribute("quizDto", quizDtoList.get(questionIndex));
            model.addAttribute("shuffled_list", shuffled_list);
            return "quiz";
        }
        return "redirect:/index";
    }

    @GetMapping(value="/result")
    public String Result(Model model) {
        final UserDto userDto = UserDetailsServiceImpl.getLoggedInUserDetails();
        if (userDto != null) {
            int points = (int) quizDtoList.stream().filter(QuizDto::isCorrectAnswer).count();
            model.addAttribute("quizDtoList", quizDtoList);
            model.addAttribute("points", points);
            final Optional<Statistics> optionalStatistics = statisticsRepository.findByUserId(userDto.getId());
            if (optionalStatistics.isPresent()) {
                final Statistics statistics = updateStatisticsEntity(optionalStatistics.get(), quizDtoList);
                statisticsRepository.save(statistics);
                model.addAttribute("score", statistics.getScore());
            }
        }
        return "result";
    }

    @GetMapping(value="/statistics")
    public String Statistics(Model model)
    {
        final UserDto userDto = UserDetailsServiceImpl.getLoggedInUserDetails();
        if (userDto != null) {
            final List<Statistics> statistics = statisticsRepository.findAll();
                model.addAttribute("statisticList", statistics );
        }
        return "statistics";
    }

    @GetMapping(value="/delete")
    public String Delete()
    {
        final UserDto userDto = UserDetailsServiceImpl.getLoggedInUserDetails();
        if (userDto != null)
        {
            final Optional<User> optionalUser = userRepository.findByUsername(userDto.getUsername());
            final User user = optionalUser.get();
            user.setActive(false);
            userRepository.save(user);
        }
        return "redirect:/logout";
    }

    private Statistics updateStatisticsEntity(final Statistics statistics, final List<QuizDto> quizDtoList) {
        final int correctAnswers = (int) quizDtoList.stream().filter(QuizDto::isCorrectAnswer).count();
        statistics.setAnsweredQuestions(statistics.getAnsweredQuestions() + questionCount);
        statistics.setCorrectAnswers(statistics.getCorrectAnswers() +
                correctAnswers);
        statistics.setFinishedTest(statistics.getFinishedTest() + 1);
        statistics.setScore(statistics.getScore() +
                userDataService.scorePoints(100f * (float)correctAnswers / questionCount, correctAnswers));
        return statistics;
    }

}
