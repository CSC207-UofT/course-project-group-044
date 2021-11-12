package Service;

import Entity.Calendar;
import Entity.Employee;
import Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeModifier {
    @Autowired
    EmployeeRepository employeeRepository;

    public Employee hireEmployee(String name, int id, int salary, int maxHoursPerWeek) {
        // New employees have an empty calendar ready for scheduling
        Calendar calendar = new Calendar();
        Employee employee = new Employee(name, id, calendar, salary, maxHoursPerWeek, true);

        employeeRepository.save(employee);
        return employee;
    }

    public Employee findEmployeeById(int id){
        return employeeRepository.findById(id).orElse(null);
    }

    public void fireEmployeebyID(int id) {
        if (employeeRepository.existsById(id)){
            employeeRepository.deleteById(id);
        }

    }

    public Double SalaryEvaluation(int id){
        // return the Salary of the person in a Week.
        Employee employee = employeeRepository.findEmployeeBy(id);
        //TODO: figure out the real total work time.
        return (double) (7 * employee.getSalary() * employee.getMaxHoursPerWeek());
    }
}
