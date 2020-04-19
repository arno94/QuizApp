package com.quizapp.quizapp.controller;
import com.quizapp.quizapp.dto.UserDto;
import com.quizapp.quizapp.entity.User;
import com.quizapp.quizapp.enums.Difficulty;
import com.quizapp.quizapp.respository.StatisticsRepository;
import com.quizapp.quizapp.respository.UserRepository;
import com.quizapp.quizapp.security.UserDetailsServiceImpl;
import com.quizapp.quizapp.util.QuizGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Date;

@Controller
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatisticsRepository statisticsRepository;

    @Autowired
    private QuizGenerator quizGenerator;

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
                               @RequestParam(value = "password", required = true) final String password) {
        // TODO már létezik te balfasz
        if (userRepository.findByUsername(username).isPresent()) {
            System.out.println("Már létezik te balfasz");
            return "/login";
        }
        // TODO sikeres belépés kezelése
        final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        final String pass = passwordEncoder.encode(password);
        User user = new User(username, pass, "ROLE_USER", new Date());
        userRepository.save(user);
        System.out.println("Registered succesfully");
        return "/login";
    }

    @GetMapping(value="/index")
    public String Index(Model model)
    {
        final UserDto userDto = UserDetailsServiceImpl.getLoggedInUserDetails();
        model.addAttribute("username",userDto.getUsername());
        model.addAttribute("solved_quiz","10");
        model.addAttribute("avg_score","80");
        model.addAttribute("rank","3");
        return "index";
    }

    @PostMapping(value="/generate_quiz")
    public RedirectView generateQuiz(final RedirectAttributes redirectAttributes,
             @RequestParam Difficulty difficulty, int questionCount) {
        final UserDto userDto = UserDetailsServiceImpl.getLoggedInUserDetails();
        if (userDto != null) {
            redirectAttributes.addFlashAttribute("difficulty", difficulty);
            redirectAttributes.addFlashAttribute("questionCount", questionCount);
            return new RedirectView("/quiz");
        }

        return new RedirectView("/index");
    }

    @RequestMapping(value="/quiz")
    public String Quiz(@ModelAttribute("difficulty") Difficulty difficulty,
                       @ModelAttribute("questionCount") Integer questionCount,
                       Model model)
    {
        final UserDto userDto = UserDetailsServiceImpl.getLoggedInUserDetails();
        model.addAttribute("questionCount",questionCount);
        if (userDto != null) {
            // TODO quiz progress
            return "quiz";
        }
        return "redirect:/index";
    }

    @GetMapping(value="/result")
    public String Result(Model model)
    {
        return "result";
    }

    @GetMapping(value="/statistics")
    public String Statistics(Model model)
    {
        return "statistics";
    }

}
