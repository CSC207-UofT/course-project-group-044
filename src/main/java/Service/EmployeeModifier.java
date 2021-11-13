package Service;

import Entity.Calendar;
import Entity.Employee;
import Entity.Organization;
import Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeModifier {
    @Autowired
    EmployeeRepository employeeRepository;

    public Employee hireEmployee(String name, int id, int salary, int hoursPerShift, int maxHoursPerWeek) {
        // New employees have an empty calendar ready for scheduling
        Calendar calendar = new Calendar();
        Employee employee = new Employee(name, id, calendar, salary, hoursPerShift, maxHoursPerWeek, true);

        employeeRepository.save(employee);
        return employee;
    }

    public void fireEmployee(Employee employee) {
        employeeRepository.delete(employee);
    }

    public Double SalaryEvaluation(int id){
        // return the Salary of the person in a Week.
        Employee employee = employeeRepository.findEmployeeBy(id);
        //TODO: figure out the real total work time.
        return (double) (7 * employee.getSalary() * employee.getMaxHoursPerWeek());
    }
}
