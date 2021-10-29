import org.junit.*;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.HashMap;
import java.time.*;

public class SchedulerTest {
    private Scheduler scheduler;
    private Organization org;
    private Employee employee;
    private EmployeeModifier mgr;

    @Before
    public void setUp() {
        scheduler = new Scheduler();
        org = new Organization();
        mgr = new EmployeeModifier(org);
        employee = mgr.hireEmployee("Sunset Shimmer", 1, 20, 20);
    }

    @After
    public void tearDown() {
    }

    // Test that a single shift can be scheduled
    @Test(timeout = 50)
    public void testScheduleShift() {
        ZonedDateTime date = ZonedDateTime.of(2013, 6, 15, 9, 0, 0, 0, ZoneOffset.UTC);
        assertNotNull(scheduler.scheduleShift(employee, date, "Canterlot", 8));
    }

    // Test that only a single shift can be scheduled for a given day
    @Test(timeout = 50)
    public void testContiguous() {
        ZonedDateTime date_1 = ZonedDateTime.of(2013, 6, 15, 9, 0, 0, 0, ZoneOffset.UTC);
        ZonedDateTime date_2 = ZonedDateTime.of(2013, 6, 15, 21, 0, 0, 0, ZoneOffset.UTC);

        assertNotNull(scheduler.scheduleShift(employee, date_1, "Canterlot", 8));
        assertNull(scheduler.scheduleShift(employee, date_2, "Canterlot", 8));
    }

    // Test that the maximum hours may be attained
    @Test(timeout = 50)
    public void testMaxHoursAttained() {
        ZonedDateTime date_1 = ZonedDateTime.of(2013, 6, 15, 9, 0, 0, 0, ZoneOffset.UTC);
        ZonedDateTime date_2 = ZonedDateTime.of(2013, 6, 16, 9, 0, 0, 0, ZoneOffset.UTC);
        ZonedDateTime date_3 = ZonedDateTime.of(2013, 6, 17, 9, 0, 0, 0, ZoneOffset.UTC);

        assertNotNull(scheduler.scheduleShift(employee, date_1, "Canterlot", 8));
        assertNotNull(scheduler.scheduleShift(employee, date_2, "Canterlot", 8));
        assertNotNull(scheduler.scheduleShift(employee, date_3, "Canterlot", 4));
    }

    // Test that only a maximum number of hours may be scheduled per week
    @Test(timeout = 50)
    public void testMaxHoursExceeded() {
        ZonedDateTime date_1 = ZonedDateTime.of(2013, 6, 15, 9, 0, 0, 0, ZoneOffset.UTC);
        ZonedDateTime date_2 = ZonedDateTime.of(2013, 6, 16, 9, 0, 0, 0, ZoneOffset.UTC);
        ZonedDateTime date_3 = ZonedDateTime.of(2013, 6, 17, 9, 0, 0, 0, ZoneOffset.UTC);

        assertNotNull(scheduler.scheduleShift(employee, date_1, "Canterlot", 8));
        assertNotNull(scheduler.scheduleShift(employee, date_2, "Canterlot", 8));
        assertNull(scheduler.scheduleShift(employee, date_3, "Canterlot", 8));
    }


}
