package com.hr.service;

import com.hr.repository.CalendarRepository;
import com.hr.repository.EmployeeRepository;
import com.hr.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service

public class Subject {
    @Autowired
    EventRepository eventRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    CalendarRepository calendarRepository;

    private List<Observer> observers = new ArrayList<>();
    private Message state;

    public void add(Observer o) {
        observers.add(o);
    }

    public void remove(Observer o){ observers.remove(o);}

    public void setter(EmployeeRepository employeeRepository, CalendarRepository calendarRepository,
                       EventRepository eventRepository){
        this.employeeRepository = employeeRepository;
        this.calendarRepository = calendarRepository;
        this.eventRepository = eventRepository;
    }

    public void setState(Message m) {
        this.state = m;
        execute(m);
    }

    private void execute(Message m) {
        for (Observer observer : observers) {
            observer.update(m, this.employeeRepository, this.calendarRepository, this.eventRepository);
        }
    }
}
