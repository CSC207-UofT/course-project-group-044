package Entity;

import Entity.Employee;

import javax.persistence.Entity;
import java.util.List;
import java.util.HashMap;

@Entity
/**
 * An entity class that stores the information of Employee.
 *
 * @param HashMap  a HashMap that stores all the Employees in this Organization.
 * @see Employee
 */
public class Organization {
    private HashMap<Integer,Employee> employees;

    /**
     * Constructor to create an instance of Organization with an empty HashMap of Employees.
     */
    public Organization(){
        this.employees = new HashMap<Integer, Employee>();
    }

    /**
     * Constructor to create an instance of Organization by setting employees.
     */
    public Organization(List<Employee> employees) {
        this.employees = new HashMap<Integer, Employee>();
        for (Employee e: employees){
            this.employees.put(e.getId(), e);
        }
    }

    /**
     * Gets the HashMap that stores all the Employees in this Organization.
     *
     * @return value of employees of this Organization.
     */
    public HashMap<Integer, Employee> getEmployees() {
        return this.employees;
    }

    /**
     * Adds an Employee to the HashMap of employees of this Organization.
     */
    public void addEmployee(Employee employee) {
        this.employees.put(employee.getId(), employee);
    }

    /**
     * Deletes a specific Employee in the HashMap of employees of this Organization by tracking its id number.
     */
    public void removeEmployee(Employee employee) {
        this.employees.remove(employee.getId());
    }
}
