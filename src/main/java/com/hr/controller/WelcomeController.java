package com.hr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("welcome")
public class WelcomeController {
    @GetMapping()
    public String getLoginPage(Model model) {
        model.addAttribute("message", "Hi csc207 students!");
        return "welcome";
    }

}
