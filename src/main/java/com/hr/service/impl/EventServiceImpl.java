package com.hr.service.impl;

import com.hr.entity.Employee;
import com.hr.entity.Event;
import com.hr.entity.Meeting;
import com.hr.entity.Shift;
import com.hr.repository.CalendarRepository;
import com.hr.repository.EmployeeRepository;
import com.hr.repository.EventRepository;
import com.hr.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service

public class EventServiceImpl implements EventService {
    @Autowired
    EventRepository eventRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    CalendarRepository calendarRepository;

    @Autowired
    Subject subject;

    @Autowired
    EventObserver eventObserver;

    @Override
    public Iterable<Event> findAllEvents(){
        return eventRepository.findAll();
    }

    /**
     * A delete Event method that could delete the event from the repository and update all the employees involved in
     * this event depending on the Event type whether it is meeting or shift.
     */
    @Override
    public void deleteEvent(Event event){
        if (event == null){
            return;
        }
        if (event instanceof Shift){
            //update employee's calendar
            Employee employee = ((Shift) event).getEmployee();
            employee.getCalendar().getEvents().remove(event);

            Message message = new Message("deleteEvent", employee, event);
            eventObserver.init(subject);
            subject.setter(employeeRepository, calendarRepository, eventRepository);
            subject.setState(message);

            eventRepository.delete(event);
        }
        else{
            Meeting meeting = (Meeting) event;
            // delete holder's calendar which contains this event
            Employee holder = meeting.getHolder();

            List<Employee> employees = meeting.getParticipants();

            Message message = new Message("deleteMeeting", holder, employees, meeting);
            eventObserver.init(subject);
            subject.setter(employeeRepository, calendarRepository, eventRepository);
            subject.setState(message);

            eventRepository.delete(meeting);
        }
        subject.remove(eventObserver);
    }

    @Override
    public void deleteEventByInstant(Instant start){
        Event event = eventRepository.findById(start).orElse(null);
        deleteEvent(event);
    }

    @Override
    public boolean deleteEventsByDate(String date) {
        LocalDate localDate = localDateconverter(date);

        List<Event> allEvents = new ArrayList<>();

        boolean hasDeleted = false;

        eventRepository.findAll().forEach(allEvents::add);

        for (Event event : allEvents) {
            if (event.getDate().isEqual(localDate)) {
                eventRepository.delete(event);
                hasDeleted = true;
            }
        }

        return hasDeleted;
    }

    @Override
    public ArrayList<Event> getEventsInSameDate(String date) {
        LocalDate localDate = localDateconverter(date);

        ArrayList<Event> allEvents = new ArrayList<>();
        ArrayList<Event> results = new ArrayList<>();

        eventRepository.findAll().forEach(allEvents::add);

        for (Event event : allEvents) {
            if (event.getDate().isEqual(localDate)) {
                results.add(event);
            }
        }

        return results;
    }

    private LocalDate localDateconverter(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);

        return localDate;
    }

    public void setter(EmployeeRepository employeeRepository, CalendarRepository calendarRepository,
                       EventRepository eventRepository){
        this.employeeRepository = employeeRepository;
        this.calendarRepository = calendarRepository;
        this.eventRepository = eventRepository;
    }
}
