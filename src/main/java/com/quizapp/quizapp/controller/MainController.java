package com.quizapp.quizapp.controller;

import com.quizapp.quizapp.dto.UserDto;
import com.quizapp.quizapp.entity.User;
import com.quizapp.quizapp.respository.UserRepository;
import com.quizapp.quizapp.security.UserDetailsServiceImpl;
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
        model.addAttribute("username","Erik");
        return "index";
    }

}
