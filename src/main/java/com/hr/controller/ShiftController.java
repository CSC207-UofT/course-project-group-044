package com.hr.controller;

import com.hr.entity.Employee;
import com.hr.entity.Event;
import com.hr.entity.Meeting;
import com.hr.entity.Shift;
import com.hr.repository.EventRepository;
import com.hr.service.impl.EventServiceImpl;
import com.hr.service.impl.SchedulerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.hr.service.EmployeeModifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.time.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("event")
public class ShiftController {

    @Autowired
    private EventServiceImpl eventService;
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
            return "eventmanager";}
        return "eventmanager";
    }
    @PostMapping("/addmeeting")
    public String addMeeting(@ModelAttribute(value="employee")Employee employee, String participants, String date,
                           String name, String location, Integer hours){

        ZonedDateTime Zonetime = localZoneconverter(date);

        ArrayList<Employee> guests = participantSeperate(participants);
        Employee user = employeeModifier.findEmployeeById(employee.getId());
        if (user != null) {
            scheduler.scheduleMeeting(user, guests, Zonetime, name, location, hours);
        return "eventmanager";}
        return "eventmanager";
    }

    @GetMapping("/displayEvent")
    public String displayEvent(Model model){
        List<Meeting> meetings = new ArrayList<>();
        List<Shift> shifts = new ArrayList<>();
        for (Event event: eventRepository.findAll()){
            if (event instanceof Meeting){
                meetings.add((Meeting) event);
            }
            else{
                shifts.add((Shift) event);
            }
        }

        model.addAttribute("meetings", meetings);
        model.addAttribute("shifts", shifts);
        model.addAttribute("employee", employeeModifier.creatingEmptyemployee());
        return "eventmanager";
    }

    @PostMapping("/removeEvent")
    public String removeEvent(@ModelAttribute(value="employee")Employee employee, String date){

        ZonedDateTime Zonetime = localZoneconverter(date);

        Employee user = employeeModifier.findEmployeeById(employee.getId());
        if (user != null) {
            Instant EventID = Zonetime.toInstant();
            eventRepository.deleteById(EventID);
            return "eventmanager";}
        return "eventmanager";
    }

    @PostMapping("/findEventByDate")
    public String findEventByDate(@ModelAttribute(value="employee")Model model, String date){
        ArrayList<Event> events;
        events = eventService.getEventsInSameDate(date);

        model.addAttribute("events", events);
        model.addAttribute("employee", employeeModifier.creatingEmptyemployee());
        return "eventmanager";
    }

    @GetMapping("/displayEventsByDate")
    public String displayEventsByDate(Model model){
        List<Event> events = new ArrayList<>();
        eventRepository.findAll().forEach(events::add);

        model.addAttribute("events", events);
        model.addAttribute("employee", employeeModifier.creatingEmptyemployee());
        return "eventmanager";
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