package com.hr.controller;

import com.hr.entity.Employee;
import com.hr.service.EmployeeModifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.hr.service.impl.EmployeeModifierImpl;

@Controller
@RequestMapping("employee")
public class HireController {
    private static Employee DUMMY = new Employee();

    @Autowired
    private EmployeeModifierImpl employeeModifier;

    @PostMapping("/hire")
    public String addEmployee(@ModelAttribute(value="employee") Employee employee, Model model){


        if (employee != null){
            employeeModifier.hireEmployee(employee.getName(), employee.getId(), employee.getSalary(),
                    employee.getMaxHoursPerWeek(), employee.getHoursPerShift());
            model.addAttribute("employee", employee);
            return "hirepage";
        }

        model.addAttribute("employee", DUMMY);
        model.addAttribute("message", "hiring failed because you add nothing");

        return "hirepage";
    }
}
