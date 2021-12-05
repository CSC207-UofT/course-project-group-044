package com.hr.controller;

import com.hr.service.EmployeeModifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("welcome")
/**
 * Controller of welcome words when employee, employeemanager, eventmanager login through the web
 */
public class WelcomeController {

    @Autowired
    EmployeeModifier employeeModifier;


    @GetMapping()
    /**
     * when an employee sign in through the web, based on its information, create employee account and display
     * the welcome words.
     *
     * @return a string of "welcome"
     */
    public String getWelcomePage(Model model) {
        model.addAttribute("message", "hello!");
        model.addAttribute("employee", employeeModifier.creatingEmptyemployee());
        return "welcome";
    }
    @GetMapping("employeemanager")
    /**
     * when an employee manager sign in through the web, based on its information, create employee account and display
     * the welcome words.
     *
     * @return a string of "employeemanager"
     */
    public String getEmployeemanagerPage(Model model) {
        model.addAttribute("message", "hello!");
        model.addAttribute("employee", employeeModifier.creatingEmptyemployee());
        return "employeemanager";
    }
    @GetMapping("eventmanager")
    /**
     * when an event manager sign in through the web, based on its information, create employee account and display
     * the welcome words.
     *
     * @return a string of "eventmanager"
     */
    public String getEventmanagerPage(Model model) {
        model.addAttribute("message", "hello!");
        model.addAttribute("employee", employeeModifier.creatingEmptyemployee());
        return "eventmanager";
    }

}