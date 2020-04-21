package com.quizapp.quizapp.controller;
import com.quizapp.quizapp.dto.UserDto;
import com.quizapp.quizapp.entity.User;
import com.quizapp.quizapp.respository.UserRepository;
import com.quizapp.quizapp.security.UserDetailsServiceImpl;
import com.quizapp.quizapp.services.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Date;

@Controller
public class MainController {

    @Autowired
    private UserDataService userDataService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String Main() {
        final UserDto userDto = UserDetailsServiceImpl.getLoggedInUserDetails();
        if (userDto != null) {
            return "redirect:/index";
        }
        return "redirect:/login";
    }

    @GetMapping(value="/login")
    public String Login_Register(Model model)
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
            System.out.println("Már létezik te balfasz");
            model.addAttribute("exists","Username already exists!");
            return "/login";
        }
        final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        final String pass = passwordEncoder.encode(password);
        User user = new User(username, pass, "ROLE_USER", new Date());
        userRepository.save(user);
        model.addAttribute("registered","Registered successfuly, please log in");

        return "/login";
    }

    @GetMapping(value="/index")
    public String Index(Model model)
    {
        final UserDto userDto = UserDetailsServiceImpl.getLoggedInUserDetails();

        model.addAttribute("username",userDto.getUsername());
        model.addAttribute("finished_test",userDataService.finishedTest());
        model.addAttribute("avg_score", userDataService.avgScore());
        model.addAttribute("rank","TO DO..");
        return "index";
    }

    @GetMapping(value="/quiz")
    public String Quiz(Model model)
    {
        return "quiz";
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
