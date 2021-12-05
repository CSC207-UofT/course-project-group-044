package com.hr.service.impl;

import com.hr.entity.*;
import com.hr.repository.CalendarRepository;
import com.hr.repository.EmployeeRepository;
import com.hr.repository.EventRepository;
import com.hr.service.EventService;
import com.hr.service.impl.EventServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class Observer {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    CalendarRepository calendarRepository;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    EventService eventService;


    public Observer(EmployeeRepository employeeRepository, CalendarRepository calendarRepository,
                    EventRepository eventRepository, EventService eventService){
        this.employeeRepository = employeeRepository;
        this.calendarRepository = calendarRepository;
        this.eventRepository = eventRepository;
        this.eventService = eventService;
    }

    //  clear all related information of the fired employee
    public void clearEmployee(Employee employee){
        UUID calendarID = employee.getCalendar().getCalendarID();
        Calendar calendar = calendarRepository.findById(calendarID).orElse(null);
        if (calendar == null){
            return;
        }
        ArrayList<Instant> ids = new ArrayList<>();
        for (Event event: calendar.getEvents()){
            ids.add(event.getStart());
        }

        calendar.getEvents().clear();

        for (Instant eventID: ids){
            eventService.deleteEventByInstant(eventID);
        }
    }

    public void createShift(Employee employee, Shift shift){
        eventRepository.save(shift);
        employee.getCalendar().addEvent(shift);
        calendarRepository.save(employee.getCalendar());
    }

    public void createMeeting(Employee host, List<Employee> participants, Meeting meeting){
        eventRepository.save(meeting);
        host.getCalendar().addEvent(meeting);
        for (Employee e:participants){
            e.getCalendar().addEvent(meeting);
            calendarRepository.save(e.getCalendar());
        }
        calendarRepository.save(host.getCalendar());
    }

//    public void removeShift(Employee employee, Shift shift){
//        eventRepository.delete(shift);
//        //I need delete method for event in Calendar here.
//    }
}
