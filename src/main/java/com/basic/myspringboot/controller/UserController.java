package com.basic.myspringboot.controller;

import com.basic.myspringboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/index")
    public ModelAndView index() {
        //model.addAttribute("users", userRepository.findAll());
        //return "index";
        return new ModelAndView("index", "users", userRepository.findAll());
    }

    @GetMapping("/thymeleaf")
    public String leaf(Model model) {

        model.addAttribute("name", "스프링부트");
        return "leaf";

    }
}