package com.hr.service;

import com.hr.repository.CalendarRepository;
import com.hr.repository.EmployeeRepository;
import com.hr.repository.EventRepository;
import org.springframework.stereotype.Service;

@Service

abstract class Observer {
    Subject subject;
    public abstract void update(Message m, EmployeeRepository employeeRepository, CalendarRepository calendarRepository,
                                EventRepository eventRepository);
}
