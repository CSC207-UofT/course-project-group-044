package com.hr.service;

import com.hr.entity.Employee;
import com.hr.entity.Meeting;
import com.hr.entity.Shift;
import com.hr.repository.CalendarRepository;
import com.hr.repository.EmployeeRepository;
import com.hr.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class EventObserver extends Observer{

    public void init(Subject subject){
        this.subject = subject;
        this.subject.add(this);
    }

    @Override
    public void update(Message m, EmployeeRepository employeeRepository, CalendarRepository calendarRepository
            , EventRepository eventRepository) {
        if (m.getS() == "createShift"){
            Employee employee = m.getEmployee();
            Shift shift = (Shift) m.getEvent();
            employee.getCalendar().addEvent(shift);
            calendarRepository.save(employee.getCalendar());
        }
        else if (m.getS() == "deleteShift"){
            Employee employee = m.getEmployee();
            Shift shift = (Shift) m.getEvent();
            employee.getCalendar().getEvents().remove(shift);
            calendarRepository.save(employee.getCalendar());
            // update employee
            employeeRepository.save(employee);
        }
        else if (m.getS() == "createMeeting"){
            Employee host = m.getHost();
            List<Employee> participants = m.getParticipants();
            Meeting meeting = (Meeting) m.getEvent();
            host.getCalendar().addEvent(meeting);
            for (Employee e:participants){
                e.getCalendar().addEvent(meeting);
                calendarRepository.save(e.getCalendar());
            }
            calendarRepository.save(host.getCalendar());
        }
        else if (m.getS() == "deleteMeeting"){
            Employee host = m.getHost();
            List<Employee> participants = m.getParticipants();
            Meeting meeting = (Meeting) m.getEvent();

            host.getCalendar().getEvents().remove(meeting);
            calendarRepository.save(host.getCalendar());
            employeeRepository.save(host);

            for (Employee employee: participants){
                employee.getCalendar().getEvents().remove(meeting);
                calendarRepository.save(employee.getCalendar());
            }
            employeeRepository.saveAll(participants);
        }
    }
}
