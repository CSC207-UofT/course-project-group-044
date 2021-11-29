package com.hr.service;

import com.hr.entity.Employee;
import com.hr.entity.Event;
import com.hr.entity.Shift;
import com.hr.repository.CalendarRepository;
import com.hr.repository.EmployeeRepository;
import com.hr.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.ZonedDateTime;

public class ShiftCreator implements EventCreator {
    Employee employee;
    ZonedDateTime date;
    String location;
    int hours;

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    CalendarRepository calendarRepository;
    @Autowired
    EventRepository eventRepository;

    public ShiftCreator(){}

    public ShiftCreator(Employee employee, ZonedDateTime date, String location, int hours){
        this.employee = employee;
        this.date = date;
        this.location = location;
        this.hours = hours;
    }

    @Override
    public Shift create() {
        // It is! Create the Shift.
        Duration duration = Duration.ofHours(hours);
        Shift shift = new Shift(employee, date.toInstant(), duration, location);

        return shift;
    }
}
