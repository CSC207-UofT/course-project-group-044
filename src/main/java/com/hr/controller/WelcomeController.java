package com.hr.controller;

import com.hr.entity.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("welcome")
public class WelcomeController {
    private static Employee DUMMY = new Employee();

    @GetMapping()
    public String getLoginPage(Model model) {
        model.addAttribute("message", "hello!");
        model.addAttribute("employee", DUMMY);
        return "welcome";
    }
    @GetMapping("1")
    public String getEmployeemanagerPage(Model model) {
        model.addAttribute("message", "hello!");
        model.addAttribute("employee", DUMMY);
        return "employeemanager";
    }
    @GetMapping("2")
    public String getEventmanagerPage(Model model) {
        model.addAttribute("message", "hello!");
        model.addAttribute("employee", DUMMY);
        return "eventmanager";
    }
}