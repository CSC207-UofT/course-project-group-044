package com.hr.controller;

import com.hr.entity.Employee;
import com.hr.service.impl.SchedulerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.hr.service.EmployeeModifier;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("employee")
public class ShiftController {
    private static Employee DUMMY = new Employee();

    @Autowired
    private SchedulerImpl scheduler;
    @Autowired
    private EmployeeModifier employeeModifier;

    public ShiftController(){}

    @PostMapping("/addshift")
    public String addShift(@ModelAttribute(value="employee")Employee employee, String date, String location,
                           Integer hours){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

        ZoneId zoneId = ZoneId.of( "Asia/Kolkata" );        //Zone information

        ZonedDateTime zdtAtAsia = dateTime.atZone( zoneId );     //Local time in Asia timezone

        Employee user = employeeModifier.findEmployeeById(employee.getId());

        if (user != null) {scheduler.scheduleShift(user, zdtAtAsia, location, hours);
            return "hirepage";}
        return "hirepage";
    }
    @PostMapping("/addmeeting")
    public String addMeeting(@ModelAttribute(value="employee")Employee employee, String participants, String date,
                           String name, String location, Integer hours){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
        ZoneId zoneId = ZoneId.of( "Asia/Kolkata" );        //Zone information
        ZonedDateTime zdtAtAsia = dateTime.atZone( zoneId );     //Local time in Asia timezone

        String[] temp = participants.split(",");
        ArrayList<Integer> participantsID = new ArrayList<>();
        for (String par: temp){
            int part = Integer.parseInt(par);
            participantsID.add(part);
        }
        Employee user = employeeModifier.findEmployeeById(employee.getId());
        ArrayList<Employee> guests= new ArrayList<>();
        for (Integer e: participantsID){
            Employee guest = employeeModifier.findEmployeeById(e); //"later on I will add "guest not found function"
            guests.add(guest);
        }
        if (user != null) {
            scheduler.scheduleMeeting(user, guests, zdtAtAsia, name, location, hours);
        return "hirepage";}
        return "hirepage";
    }


    @PostMapping("/removeshift")
    public String removeShift(@ModelAttribute(value="employee")Employee employee, ZonedDateTime date, String location, Integer hours){
        Employee user = employeeModifier.findEmployeeById(employee.getId());
        if (user != null) {scheduler.cancelShift(user, date, location, hours);
        return "hirepage";}
        return "hirepage";
    }
    @PostMapping("/removemeeting")
    public String removeMeeting(@ModelAttribute(value="employee")Employee employee, List<Employee> participants, ZonedDateTime date,
                              String name, String location, Integer hours){
        Employee user = employeeModifier.findEmployeeById(employee.getId());
        if (user != null){scheduler.cancelMeeting(user, participants, date, location, name, hours);
        return "hirepage";}
        return "hirepage";
    }
}