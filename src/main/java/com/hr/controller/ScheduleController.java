package com.hr.controller;

import com.hr.entity.Employee;
import com.hr.service.impl.EmployeeModifierImpl;
import com.hr.service.impl.SchedulerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("schedule")
/**
 * Controller for scheduling employee
 *
 * @see SchedulerImpl
 */
public class ScheduleController {

    @Autowired
    private  SchedulerImpl sched;

    @Autowired
    private EmployeeModifierImpl employeeModifier;

    @GetMapping("/schedule")
    /**
     * check whether the employee is available at specific time, and then schedule its work
     *
     * @return a string of "schedule"
     */
    public String schedule(Model model){

        List<Employee> employees = new ArrayList<>(employeeModifier.findAllEmployees());

        ZonedDateTime base = ZonedDateTime.of(2021, 11, 8, 9, 0, 0, 0, ZoneOffset.UTC);

        boolean succ = sched.scheduleWeek(employees, base);

        model.addAttribute("status", succ ? "success" : "fail");
        model.addAttribute("employees", employeeModifier.findAllEmployees());
        return "schedule";
    }
}
