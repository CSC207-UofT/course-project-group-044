import com.hr.entity.Employee;
import com.hr.repository.EmployeeRepository;
import com.hr.repository.CalendarRepository;
import com.hr.service.impl.EmployeeModifierImpl;
import com.hr.service.impl.SchedulerImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.ArrayList;
import java.time.*;

@ExtendWith(MockitoExtension.class)
public class SchedulerTest {
    private SchedulerImpl scheduler;
    private Employee employee;

    @InjectMocks
    private EmployeeModifierImpl mgr;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private CalendarRepository calendarRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        employee = mgr.hireEmployee("Sunset Shimmer", 1, 20, 20, 4);
        scheduler = new SchedulerImpl();
    }

    @AfterEach
    public void tearDown() {
    }

    // Test that a single shift can be scheduled
    @Test
    public void testScheduleShift() {
        ZonedDateTime date = ZonedDateTime.of(2013, 6, 15, 9, 0, 0, 0, ZoneOffset.UTC);
        assertNotNull(scheduler.scheduleShift(employee, date, "Canterlot", 8));
    }

    // Test that only a single shift can be scheduled for a given day
    @Test
    public void testContiguous() {
        ZonedDateTime date1 = ZonedDateTime.of(2013, 6, 15, 9, 0, 0, 0, ZoneOffset.UTC);
        ZonedDateTime date2 = ZonedDateTime.of(2013, 6, 15, 21, 0, 0, 0, ZoneOffset.UTC);

        assertNotNull(scheduler.scheduleShift(employee, date1, "Canterlot", 8));
        assertNull(scheduler.scheduleShift(employee, date2, "Canterlot", 8));
    }

    // Test that the maximum hours may be attained
    @Test
    public void testMaxHoursAttained() {
        ZonedDateTime date1 = ZonedDateTime.of(2013, 6, 15, 9, 0, 0, 0, ZoneOffset.UTC);
        ZonedDateTime date2 = ZonedDateTime.of(2013, 6, 16, 9, 0, 0, 0, ZoneOffset.UTC);
        ZonedDateTime date3 = ZonedDateTime.of(2013, 6, 17, 9, 0, 0, 0, ZoneOffset.UTC);

        assertNotNull(scheduler.scheduleShift(employee, date1, "Canterlot", 8));
        assertNotNull(scheduler.scheduleShift(employee, date2, "Canterlot", 8));
        assertNotNull(scheduler.scheduleShift(employee, date3, "Canterlot", 4));
    }

    // Test that only a maximum number of hours may be scheduled per week
    @Test
    public void testMaxHoursExceeded() {
        ZonedDateTime date1 = ZonedDateTime.of(2013, 6, 15, 9, 0, 0, 0, ZoneOffset.UTC);
        ZonedDateTime date2 = ZonedDateTime.of(2013, 6, 16, 9, 0, 0, 0, ZoneOffset.UTC);
        ZonedDateTime date3 = ZonedDateTime.of(2013, 6, 17, 9, 0, 0, 0, ZoneOffset.UTC);

        assertNotNull(scheduler.scheduleShift(employee, date1, "Canterlot", 8));
        assertNotNull(scheduler.scheduleShift(employee, date2, "Canterlot", 8));
        assertNull(scheduler.scheduleShift(employee, date3, "Canterlot", 8));
    }


}
