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

    @GetMapping("/")
    @ResponseBody
    public String test() {
        final List<User> userList = userRepository.findAll();
        return userList.size() + "";
    }

}
