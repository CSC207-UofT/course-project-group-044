package Service;

import Entity.Calendar;
import Entity.Employee;
import Entity.Organization;
import Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
/**
 * A Manager has the power to modify employees in this Organization.
 * has permission to hire and fire Employee from the Organization, and evaluate payroll .
 *
 * @param employeeRepository  the database that stores and accesses all Employees.
 * @see EmployeeRepository
 */

public class EmployeeModifier {
    @Autowired
    EmployeeRepository employeeRepository;

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
    public Employee hireEmployee(String name, int id, int salary, int maxHoursPerWeek) {
        // New employees have an empty calendar ready for scheduling
        Calendar calendar = new Calendar();
        Employee employee = new Employee(name, id, calendar, salary, maxHoursPerWeek, true);

        employeeRepository.save(employee);
        return employee;
    }

    /**
     * Fire an employee from the Organization.
     */
    public void fireEmployee(Employee employee) {
        employeeRepository.delete(employee);
    }

    /**
     * Evaluate the weekly salary of an Employee with given id.
     *
     * @see Employee#getSalary()
     * @see Employee#getMaxHoursPerWeek()
     */
    public Double SalaryEvaluation(int id){
        // return the Salary of the person in a Week.
        Employee employee = employeeRepository.findEmployeeBy(id);
        //TODO: figure out the real total work time.
        return (double) (7 * employee.getSalary() * employee.getMaxHoursPerWeek());
    }
}
