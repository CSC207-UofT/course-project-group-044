package Service;

import Entity.Calendar;
import Entity.Employee;
import Entity.Organization;
import Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeModifier implements EmployeeRepository{
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

    public Double SalaryEvaluation(int id){
        // return the Salary of the person in a Week.
        Employee employee = employeeRepository.findEmployeeById(id);
        //TODO: figure out the real total work time.
        return (double) (7 * employee.getSalary() * employee.getMaxHoursPerWeek());
    }
}
