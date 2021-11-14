package com.hr.controller;

import com.hr.entity.Employee;
import com.hr.service.EmployeeModifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.hr.service.impl.EmployeeModifierImpl;

import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("employee")
public class FireController {
    private static Employee DUMMY = new Employee();

    @Autowired
    private EmployeeModifierImpl employeeModifier;

    @PostMapping("/fire")
    public String deleteEmployee(@ModelAttribute(value="employee") Employee employee, Model model){

        Employee user = employeeModifier.findEmployeeById(employee.getId());

        if (!Objects.equals(user, Optional.empty())){
            employeeModifier.findEmployeeById(user.getId());
            model.addAttribute("employee", employee);
            return "firepage";
        }
        model.addAttribute("employee", DUMMY);
        model.addAttribute("message", "firing failed because you add nothing");

        return "firepage";
    }
}
