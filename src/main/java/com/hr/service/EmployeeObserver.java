package com.hr.service;

import com.hr.entity.Calendar;
import com.hr.entity.Employee;
import com.hr.entity.Event;
import com.hr.repository.CalendarRepository;
import com.hr.repository.EmployeeRepository;
import com.hr.repository.EventRepository;
import com.hr.service.impl.EventServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;

@Service

public class EmployeeObserver extends Observer{

    @Autowired
    EventServiceImpl eventService;

    public void init(Subject subject){
        this.subject = subject;
        this.subject.add(this);
    }

    @Override
    public void update(Message m, EmployeeRepository employeeRepository, CalendarRepository calendarRepository,
                       EventRepository eventRepository) {
        if (m.getS() == "deleteEmployee"){
            eventService.setter(employeeRepository, calendarRepository, eventRepository);
            Employee employee = m.getEmployee();
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
    }

}
