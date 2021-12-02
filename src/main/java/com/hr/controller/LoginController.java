package com.hr.controller;
import com.hr.entity.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Observer;

@Controller
@RequestMapping("login")
public class LoginController {
    public boolean login = false;

    @GetMapping()
    public String getLoginPage(Model model) {
        model.addAttribute("message", "hello!");
        return "login";
    }
    }
