package com.hr.controller;

import com.hr.entity.Employee;
import com.hr.repository.EmployeeRepository;
import com.hr.service.EmployeeModifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("welcome")
public class WelcomeController {

    @Autowired
    EmployeeModifier employeeModifier;

    @GetMapping()
    public String getWelcomePage(Model model) {
        model.addAttribute("message", "hello!");
        model.addAttribute("employee", employeeModifier.creatingEmptyemployee());
        return "welcome";
    }
    @GetMapping("employeemanager")
    public String getEmployeemanagerPage(Model model) {
        model.addAttribute("message", "hello!");
        model.addAttribute("employee", employeeModifier.creatingEmptyemployee());
        return "employeemanager";
    }
    @GetMapping("eventmanager")
    public String getEventmanagerPage(Model model) {
        model.addAttribute("message", "hello!");
        model.addAttribute("employee", employeeModifier.creatingEmptyemployee());
        return "eventmanager";
    }
}