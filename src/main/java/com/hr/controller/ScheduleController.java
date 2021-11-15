package com.hr.controller;

import com.hr.entity.Employee;
import com.hr.entity.Event;
import com.hr.service.EmployeeModifier;
import com.hr.service.Scheduler;
import com.hr.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Controller
@RequestMapping("schedule")
public class ScheduleController {
    @Autowired
    EmployeeRepository employeeRepository;

    private static Employee DUMMY = new Employee();

    @Autowired
    private EmployeeModifier employeeModifier;

    @GetMapping("/schedule")
    public String schedule(Model model){
        List<Employee> employees = new ArrayList<>();

        for (Employee it : employeeRepository.findAll()) {
            employees.add(it);
        }

        Scheduler sched = new Scheduler(employees);
        ZonedDateTime base = ZonedDateTime.of(2021, 11, 8, 9, 0, 0, 0, ZoneOffset.UTC);

        boolean succ = sched.scheduleWeek(base);

        model.addAttribute("status", succ ? "success" : "fail");
        model.addAttribute("employees", employeeRepository.findAll());
        return "schedule";
    }
}
