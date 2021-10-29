import java.util.ArrayList;
import java.util.List;

public class EmployeeModifier {
    private final Organization organization;

    public EmployeeModifier(Organization organization) {
        this.organization = organization;
    }

    public Employee hireEmployee(String name, int id, int salary, int maxHoursPerWeek) {
        // New employees have an empty calendar ready for scheduling
        Calendar calendar = new Calendar();
        Employee employee = new Employee(name, id, calendar, salary, maxHoursPerWeek, true);

        this.organization.addEmployee(employee);
        return employee;
    }

    public void fireEmployee(Employee employee) {
        this.organization.removeEmployee(employee);
    }
}
