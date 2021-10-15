import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.ArrayList;

public class Dummy {
    public static void main(String[] args) {
        // Create some entity classes. In the future, these would come from a
        // database. 
        Shift shift = new Shift(Instant.now(), Duration.ofHours(4), "Canterlot Maki");
        Calendar calendar = new Calendar();
        Employee employee = new Employee("Sunset Shimmer", 1, "Sushi waitress", calendar, 15, 20, true);

        calendar.addEvent(shift);

        Organization org = new Organization();
        org.addEmployee(employee);

		System.out.println("Hello, World!\n");
	}
}
