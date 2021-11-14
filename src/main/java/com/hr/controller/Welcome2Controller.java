package com.hr.controller;

import com.hr.entity.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("welcome2")
public class Welcome2Controller {
    private static Employee DUMMY = new Employee();
    @GetMapping()
    public String getLoginPage(Model model) {
        model.addAttribute("message", "welcome to Hr system!");
        model.addAttribute("employee", DUMMY);
        return "firepage";
    }
}