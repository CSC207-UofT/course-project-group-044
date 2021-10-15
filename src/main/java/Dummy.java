import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.ArrayList;

public class Dummy {
    // Pretty prints the contents of the organization for debug
    public static void printOrganization(Organization org) {
		System.out.println("Organization:\n");

        for (Employee it : org.getEmployees()) {
            System.out.println(String.format("%d: %s (%s), $%d CAD/hr, %d hr/wk",
                        it.id, it.name, it.title, it.salary, it.maxHoursPerWeek));
        }

        System.out.println("\n");
    }

    public static void main(String[] args) {
        // Create some entity classes. In the future, these would come from a
        // database. 
        Organization org = new Organization();
        EmployeeManager employeeManager = new EmployeeManager(org);

        System.out.println("On start:\n");
        printOrganization(org);

        Employee employee = employeeManager.hireEmployee("Jack", 1, "Sushi waiter", 15, 20);
        Shift shift = new Shift(Instant.now(), Duration.ofHours(4), "Canterlot Maki");
        employee.calendar.addEvent(shift);

        System.out.println("After hiring:\n");
        printOrganization(org);

        employeeManager.fireEmployee(employee);

        System.out.println("After firing:\n");
        printOrganization(org);
	}
}
