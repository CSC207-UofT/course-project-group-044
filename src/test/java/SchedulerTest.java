import Entity.Employee;
import Entity.Organization;
import Entity.Calendar;
import Entity.Shift;
import Service.Scheduler;
import org.junit.*;

import static org.junit.Assert.*;

import java.time.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class SchedulerTest {
    private Employee employee;
    private Scheduler scheduler;

    @Before
    public void setUp() {
        Calendar calendar = new Calendar();
        employee = new Employee("Sunset Shimmer", 1, calendar, 20,
                20, 4, true);
        scheduler = new Scheduler();
        Organization org = new Organization();
        org.addEmployee(employee);
    }

    @After
    public void tearDown() {
    }

    // Test that a single shift can be scheduled
    @Test(timeout = 50)
    public void testScheduleShift() {
        ZonedDateTime date = ZonedDateTime.of(2013, 6, 15, 9,
                0, 0, 0, ZoneOffset.UTC);
        Shift actualShift = scheduler.scheduleShift(employee, date, "Canterlot", 8);

        assertEquals(new Shift(employee, Instant.parse("2013-06-15T09:00:00Z"),
                Duration.ofHours(8), "Canterlot"), actualShift);
    }

    // Test that whether the shift has been updated to a given employee's calendar.
    @Test(timeout = 50)
    public void testCalenderUpdate() {
        ZonedDateTime date = ZonedDateTime.of(2013, 6, 15, 9,
                0, 0, 0, ZoneOffset.UTC);
        scheduler.scheduleShift(employee, date, "Canterlot", 8);
        Calendar actualCalendar = employee.getCalendar();

        Calendar expectCalendar = new Calendar();
        expectCalendar.addEvent(new Shift(employee, Instant.parse("2013-06-15T09:00:00Z"),
                Duration.ofHours(8), "Canterlot"));

        assertEquals(expectCalendar, actualCalendar);
    }

    // Test that assigning two shifts which are overlapped to a given employee.
    @Test(timeout = 50)
    public void testOverlappedShifts() {
        ZonedDateTime date1 = ZonedDateTime.of(2013, 6, 15, 9,
                0, 0, 0, ZoneOffset.UTC);
        ZonedDateTime date2 = ZonedDateTime.of(2013, 6, 15, 10,
                0, 0, 0, ZoneOffset.UTC);
        scheduler.scheduleShift(employee, date1, "Canterlot", 8);
        scheduler.scheduleShift(employee, date2, "Canterlot", 8);

        Calendar expectCalendar = new Calendar();
        expectCalendar.addEvent(new Shift(employee, Instant.parse("2013-06-15T09:00:00Z"),
                Duration.ofHours(8), "Canterlot")); // the second shift should not assign to employee,
                                                            // since it overlapped to date1
        assertEquals(expectCalendar, employee.getCalendar());
    }

    // Test that only a single shift can be scheduled for a given day
    @Test(timeout = 50)
    public void testContiguous() {
        ZonedDateTime date1 = ZonedDateTime.of(2013, 6, 15, 9, 0, 0, 0, ZoneOffset.UTC);
        ZonedDateTime date2 = ZonedDateTime.of(2013, 6, 15, 21, 0, 0, 0, ZoneOffset.UTC);

        assertNotNull(scheduler.scheduleShift(employee, date1, "Canterlot", 8));
        assertNull(scheduler.scheduleShift(employee, date2, "Canterlot", 8));
    }

    // Test that the maximum hours may be attained
    @Test(timeout = 50)
    public void testMaxHoursAttained() {
        ZonedDateTime date1 = ZonedDateTime.of(2013, 6, 15, 9, 0, 0, 0, ZoneOffset.UTC);
        ZonedDateTime date2 = ZonedDateTime.of(2013, 6, 16, 9, 0, 0, 0, ZoneOffset.UTC);
        ZonedDateTime date3 = ZonedDateTime.of(2013, 6, 17, 9, 0, 0, 0, ZoneOffset.UTC);

        assertNotNull(scheduler.scheduleShift(employee, date1, "Canterlot", 8));
        assertNotNull(scheduler.scheduleShift(employee, date2, "Canterlot", 8));
        assertNotNull(scheduler.scheduleShift(employee, date3, "Canterlot", 4));
    }

    // Test that only a maximum number of hours may be scheduled per week
    @Test(timeout = 50)
    public void testMaxHoursExceeded() {
        ZonedDateTime date1 = ZonedDateTime.of(2013, 6, 15, 9, 0, 0, 0, ZoneOffset.UTC);
        ZonedDateTime date2 = ZonedDateTime.of(2013, 6, 16, 9, 0, 0, 0, ZoneOffset.UTC);
        ZonedDateTime date3 = ZonedDateTime.of(2013, 6, 17, 9, 0, 0, 0, ZoneOffset.UTC);

        assertNotNull(scheduler.scheduleShift(employee, date1, "Canterlot", 8));
        assertNotNull(scheduler.scheduleShift(employee, date2, "Canterlot", 8));
        assertNull(scheduler.scheduleShift(employee, date3, "Canterlot", 8));
    }


}
