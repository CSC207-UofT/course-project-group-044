package com.hr.controller;

import com.hr.entity.Employee;
import com.hr.service.EmployeeModifier;
import com.hr.service.impl.EmployeeModifierImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("employee")
public class HireController {

    @Autowired
    private EmployeeModifierImpl employeeModifierImpl;

    @PostMapping("/hire")
    public String addEmployee(@ModelAttribute(value="employee") Employee employee, Model model){


        if (employee != null){
            employeeModifierImpl.hireEmployee(employee.getName(), employee.getId(), employee.getSalary(),
                    employee.getMaxHoursPerWeek(), employee.getHoursPerShift());
            model.addAttribute("employee", employee);
            return "employeemanager";
        }

        model.addAttribute("message", "hiring failed because lacking information");

        return "employeemanager";
    }
}
