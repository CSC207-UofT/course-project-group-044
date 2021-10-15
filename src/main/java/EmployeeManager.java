public class EmployeeManager {
    private final Organization organization;

    public EmployeeManager(Organization organization) {
        this.organization = organization;
    }

    public Employee hireEmployee(String name, int id, String title, int salary, int maxHoursPerWeek) {
        // New employees have an empty calendar ready for scheduling
        Calendar calendar = new Calendar();
        Employee employee = new Employee(name, id, title, calendar, salary, maxHoursPerWeek, true);

        this.organization.addEmployee(employee);
        return employee;
    }

    public void fireEmployee(Employee employee) {
        this.organization.removeEmployee(employee);
    }
}
