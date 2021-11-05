import Entity.Employee;
import Entity.Organization;
import Entity.Event;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class Dummy {
    // Pretty prints the contents of the organization for debug
    public static void printOrganization(Organization org) {
		System.out.println("Organization:\n");

        for (Employee it : org.getEmployees().values()) {
            System.out.println("ID: " + it.getId() + ", Name: " + it.getName() +
                    ", Salary: $" + it.getSalary() + "/hour" +
                    ", Max Hours Per Week: " + it.getMaxHoursPerWeek() + " h");

            for (Event event : it.getCalendar().getEvents()) {
                System.out.println(event.getDate(ZoneOffset.UTC).toString() +
                                   " (" + event.getHours() + " hours)");
            }

            System.out.println("\n");
        }

        System.out.println("\n");
    }

    public static void main(String[] args) {
        // Create some entity classes. In the future, these would come from a
        // database. 
        Organization org = new Organization();
        EmployeeModifier employeeManager = new EmployeeModifier(org);

        System.out.println("On start:\n");
        printOrganization(org);

        employeeManager.hireEmployee("Pinkie Pie", 1, 15, 20, 4);
        employeeManager.hireEmployee("Twilight Sparkle", 2, 15, 20, 4);
        employeeManager.hireEmployee("Rarity", 3, 15, 20, 4);
        employeeManager.hireEmployee("Rainbow Dash", 4, 15, 20, 4);
        employeeManager.hireEmployee("Fluttershy", 5, 15, 20, 4);
        employeeManager.hireEmployee("Applejack", 6, 15, 20, 4);

        Employee jack = employeeManager.hireEmployee("Jack", 7, 15, 20, 4);

        System.out.println("After hiring:\n");
        printOrganization(org);

        employeeManager.fireEmployee(jack);

        System.out.println("After firing:\n");
        printOrganization(org);

        Scheduler sched = new Scheduler(new ArrayList(org.getEmployees().values()));
        ZonedDateTime base = ZonedDateTime.of(2021, 11, 8, 9, 0, 0, 0, ZoneOffset.UTC);

        if (sched.scheduleWeek(base)) {
            System.out.println("After scheduling:\n");
            printOrganization(org);
        } else {
            System.out.println("Scheduling failed.\n");
        }
	}
}
