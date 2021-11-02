import Entity.Calendar;
import Entity.Employee;
import Entity.Organization;

public class EmployeeModifier {
    private final Organization organization;

    public EmployeeModifier(Organization organization) {
        this.organization = organization;
    }

    public Employee hireEmployee(String name, int id, int salary, int maxHoursPerWeek, int hoursPerShift) {
        // New employees have an empty calendar ready for scheduling
        Calendar calendar = new Calendar();
        Employee employee = new Employee(name, id, calendar, salary, maxHoursPerWeek, hoursPerShift, true);

        this.organization.addEmployee(employee);
        return employee;
    }

    public void fireEmployee(Employee employee) {
        this.organization.removeEmployee(employee);
    }
}
