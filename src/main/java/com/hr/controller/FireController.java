package com.hr.controller;

import com.hr.entity.Employee;
import com.hr.service.EmployeeModifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("employee")
public class FireController {

    @Autowired
    private EmployeeModifier employeeModifier;

    @PostMapping("/fire")
    public String deleteEmployee(@ModelAttribute(value="employee") Employee employee, Model model){

        Employee user = employeeModifier.findEmployeeById(employee.getId());

        if (user != null){


            employeeModifier.fireEmployee(user);
            model.addAttribute("employee", employee);
            return "employeemanager";
        }
        model.addAttribute("message", "firing failed because you add nothing");

        return "employeemanager";
    }
}
