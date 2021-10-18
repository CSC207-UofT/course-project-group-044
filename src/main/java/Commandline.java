import java.time.Duration;
import java.time.Instant;

public class Commandline {
    // Pretty prints the contents of the organization for debug
    public static void printOrganization(Organization org) {
        System.out.println("Organization:\n");

        for (Employee it : org.getEmployees().values()) {
            System.out.println("ID: " + it.getId() + ", Name: " + it.getName() +
                    ", Salary: $" + it.getSalary() + "/hour" +
                    ", Max Hours Per Week: " + it.getMaxHoursPerWeek() + " h");
        }

        System.out.println("\n");
    }


    public static void main(String[] args) {

        ManagementSystem em = new ManagementSystem();
        SystemInOut inOut = new SystemInOut();
        em.run(inOut);

        // Create some entity classes. In the future, these would come from a
        // database.
        Organization org = new Organization();
        EmployeeModifier employeeManager = new EmployeeModifier(org);

        System.out.println("On start:\n");
        printOrganization(org);

        Employee employee = employeeManager.hireEmployee("Jack", 1, 15, 20);
        Shift shift = new Shift(employee, Instant.now(), Duration.ofHours(4), "Canterlot Maki");
        employee.addEvent(shift);

        System.out.println("After hiring:\n");
        printOrganization(org);

        employeeManager.fireEmployee(employee);

        System.out.println("After firing:\n");
        printOrganization(org);


    }
}
