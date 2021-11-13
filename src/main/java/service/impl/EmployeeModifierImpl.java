package service.impl;

import entity.Calendar;
import entity.Employee;
import repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.EmployeeModifier;


@Service
public class EmployeeModifierImpl implements EmployeeModifier {
    @Autowired
    EmployeeRepository employeeRepository;

    public Employee hireEmployee(String name, int id, int salary, int maxHoursPerWeek, int hoursPerShift) {
        // New employees have an empty calendar ready for scheduling
        Calendar calendar = new Calendar();
        Employee employee = new Employee(name, id, calendar, salary, maxHoursPerWeek, hoursPerShift, true);

        employeeRepository.save(employee);
        return employee;
    }

    public void fireEmployee(Employee employee) {
        employeeRepository.delete(employee);
    }

    @Override
    public Employee findEmployeeById(int id){
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    public Double evaluateSalary(int id){
        // return the Salary of the person in a Week.
        Employee employee = findEmployeeById(id);
        //TODO: figure out the real total work time.
        return (double) (7 * employee.getSalary() * employee.getMaxHoursPerWeek());
    }
}
