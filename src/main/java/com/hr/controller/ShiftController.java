package com.hr.controller;

import com.hr.entity.Employee;
import com.hr.entity.Event;
import com.hr.repository.EventRepository;
import com.hr.service.impl.SchedulerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.hr.service.EmployeeModifier;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Controller
@RequestMapping("employee")
public class ShiftController {
    private static Employee DUMMY = new Employee();

    @Autowired
    private SchedulerImpl scheduler;
    @Autowired
    private EmployeeModifier employeeModifier;
    @Autowired
    private EventRepository eventRepository;

    public ShiftController(){}

    @PostMapping("/addshift")
    public String addShift(@ModelAttribute(value="employee")Employee employee, String date, String location,
                           Integer hours){

        ZonedDateTime Zonetime = localZoneconverter(date);

        Employee user = employeeModifier.findEmployeeById(employee.getId());

        if (user != null) {scheduler.scheduleShift(user, Zonetime, location, hours);
            return "hirepage";}
        return "hirepage";
    }
    @PostMapping("/addmeeting")
    public String addMeeting(@ModelAttribute(value="employee")Employee employee, String participants, String date,
                           String name, String location, Integer hours){

        ZonedDateTime Zonetime = localZoneconverter(date);

        ArrayList<Employee> guests = participantSeperate(participants);
        Employee user = employeeModifier.findEmployeeById(employee.getId());
        if (user != null) {
            scheduler.scheduleMeeting(user, guests, Zonetime, name, location, hours);
        return "hirepage";}
        return "hirepage";
    }


    @PostMapping("/removeEvent")
    public String removeEvent(@ModelAttribute(value="employee")Employee employee, String date){

        ZonedDateTime Zonetime = localZoneconverter(date);

        Employee user = employeeModifier.findEmployeeById(employee.getId());
        if (user != null) {
            Instant EventID = Zonetime.toInstant();
            eventRepository.deleteById(EventID);
            return "hirepage";}
        return "hirepage";
    }

    private ZonedDateTime localZoneconverter(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
        ZoneId zoneId = ZoneId.of( "Asia/Kolkata" );        //Zone information
        return dateTime.atZone( zoneId );
    }

    private ArrayList<Employee> participantSeperate(String participants){

        String[] temp = participants.split(",");
        ArrayList<Integer> participantsID = new ArrayList<>();
        for (String par: temp){
            int part = Integer.parseInt(par);
            participantsID.add(part);
        }
        ArrayList<Employee> guests= new ArrayList<>();
        for (Integer e: participantsID){
            Employee guest = employeeModifier.findEmployeeById(e); //"later on I will add "guest not found function"
            guests.add(guest);
        }
        return guests;
    }

}