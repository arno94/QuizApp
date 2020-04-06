package com.quizapp.quizapp.controller;

import com.quizapp.quizapp.entity.User;
import com.quizapp.quizapp.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private UserRepository userRepository;

    private boolean isLoggedIn() {
        return false;
    }

    @GetMapping("/")
    public String Main() {
        if (isLoggedIn()) {
            return "redirect:/index";
        }
        return "login_register";
    }

    @GetMapping(value="/login_register")
    public String Login_Register()
    {
        return "login_register";
    }

    @GetMapping(value="/index")
    public String Index()
    {
        return "index";
    }

}
