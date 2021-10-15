import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.ArrayList;

public class Dummy {
    public static void main(String[] args) {
        // Create some entity classes. In the future, these would come from a
        // database. 
        Organization org = new Organization();
        EmployeeManager employeeManager = new EmployeeManager(org);
        Employee employee = employeeManager.hireEmployee("Sunset Shimmer", 1, "Sushi waitress", 15, 20);

        Shift shift = new Shift(Instant.now(), Duration.ofHours(4), "Canterlot Maki");
        employee.calendar.addEvent(shift);

		System.out.println("Hello, World!\n");
	}
}
