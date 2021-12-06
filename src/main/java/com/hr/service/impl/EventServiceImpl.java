package com.hr.service.impl;

import com.hr.entity.Employee;
import com.hr.entity.Event;
import com.hr.entity.Meeting;
import com.hr.entity.Shift;
import com.hr.repository.CalendarRepository;
import com.hr.repository.EmployeeRepository;
import com.hr.repository.EventRepository;
import com.hr.service.EventService;
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
            calendarRepository.save(employee.getCalendar());
            // update employee
            employeeRepository.save(employee);

            eventRepository.delete(event);
        }
        else{
            Meeting meeting = (Meeting) event;
            // delete holder's calendar which contains this event
            Employee holder = meeting.getHolder();
            holder.getCalendar().getEvents().remove(meeting);
            calendarRepository.save(holder.getCalendar());
            employeeRepository.save(holder);
            // delete all Events in participants' calendar
            List<Employee> employees = meeting.getParticipants();
            for (Employee employee: employees){
                employee.getCalendar().getEvents().remove(meeting);
                calendarRepository.save(employee.getCalendar());
            }
            employeeRepository.saveAll(employees);

            eventRepository.delete(meeting);
        }
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
}
