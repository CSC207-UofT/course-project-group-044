package com.hr.service;

import com.hr.entity.Employee;
import com.hr.entity.Event;
import com.hr.entity.Meeting;
import com.hr.repository.CalendarRepository;
import com.hr.repository.EmployeeRepository;
import com.hr.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;

public class MeetingCreator implements EventCreator{
    Employee host;
    List<Employee> participants;
    ZonedDateTime date;
    String name;
    String location;
    int hours;

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    CalendarRepository calendarRepository;
    @Autowired
    EventRepository eventRepository;

    public MeetingCreator(){}

    public MeetingCreator(Employee host, List<Employee> participants, ZonedDateTime date,
                          String name, String location, int hours){
        this.host = host;
        this.participants = participants;
        this.date = date;
        this.name = name;
        this.location = location;
        this.hours = hours;
    }

    @Override
    public Meeting create() {
        // It is! Create the Shift.
        Duration duration = Duration.ofHours(hours);
        Meeting meeting = new Meeting(host, participants, date.toInstant(), duration, name, location);

        this.eventRepository.save(meeting);
        host.getCalendar().addEvent(meeting);
        for (Employee e:participants){
            e.getCalendar().addEvent(meeting);
            this.calendarRepository.save(e.getCalendar());
        }
        this.calendarRepository.save(host.getCalendar());
        return meeting;
    }
}
