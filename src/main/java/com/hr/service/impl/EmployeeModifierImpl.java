package com.hr.service.impl;

import com.hr.entity.Calendar;
import com.hr.entity.Employee;
import com.hr.entity.Event;
import com.hr.repository.CalendarRepository;
import com.hr.repository.EmployeeRepository;
import com.hr.repository.EventRepository;
import com.hr.service.EmployeeModifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
/*
 * A Manager has the power to modify employees in this Organization.
 * has permission to hire and fire Employee from the Organization, and evaluate payroll .
 *
 * @param employeeRepository  the database that stores and accesses all Employees.
 * @see EmployeeRepository
 */
public class EmployeeModifierImpl implements EmployeeModifier {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    CalendarRepository calendarRepository;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    private EventServiceImpl eventService;

    public EmployeeModifierImpl() {}

    /**
     * Hire a new employee to the organization. Create a new Employee by setting its id, salary, maxHoursPerWeek.
     * Assign a empty Calendar for this Employee.
     * Add the data of this Employee into employeeRepository.
     *
     * @param name             the name of this Employee.
     * @param id               the specific id of this Employee.
     * @param salary           the salary of this Employee.
     * @param maxHoursPerWeek  the max hours that this employee can work in a week.
     * @return the employee that is added to the Organization.
     */
    @Override
    public Employee hireEmployee(String name, int id, int salary, int maxHoursPerWeek, int hoursPerShift) {
        // New employees have an empty calendar ready for scheduling
        Calendar calendar = new Calendar();
        Employee employee = new Employee(name, id, calendar, salary, maxHoursPerWeek, hoursPerShift, true);

        calendarRepository.save(calendar);
        employeeRepository.save(employee);
        return employee;
    }
    /**
     * Fire an employee from the Organization.
     * precondition: employee must in the database
     */
    @Override
    public void fireEmployee(Employee employee) {
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

        employeeRepository.delete(employee);
    }

    @Override
    public List<Employee> findAllEmployees(){
        List<Employee> employees = new ArrayList<>();
        for (Employee employee: employeeRepository.findAll()){
            employees.add(employee);
        }
        return employees;
    }

    @Override
    public Employee findEmployeeById(int id){
        return employeeRepository.findById(id).orElse(null);
    }


    @Override
    /*
     * Evaluate the weekly salary of an Employee with given id.
     *
     * @see Employee#getSalary()
     * @see Employee#getMaxHoursPerWeek()
     */
    public double evaluateSalary(Employee employee) {
        //TODO: figure out the real total work time.
        return 7 * employee.getSalary() * employee.getMaxHoursPerWeek();
    }
}
