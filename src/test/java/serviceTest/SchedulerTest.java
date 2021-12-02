package serviceTest;

import com.hr.entity.*;
import com.hr.repository.CalendarRepository;
import com.hr.repository.EmployeeRepository;
import com.hr.repository.EventRepository;
import com.hr.service.impl.EmployeeModifierImpl;
import com.hr.service.impl.SchedulerImpl;
import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class SchedulerTest {
    private Employee employee;

    @InjectMocks
    private SchedulerImpl scheduler;
    private EmployeeModifierImpl mgr;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private CalendarRepository calendarRepository;

    @Mock
    private EventRepository eventRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        scheduler = new SchedulerImpl(employeeRepository, calendarRepository, eventRepository);
    }

//    @After
//    public void tearDown() {
//    }

    // Test that a single shift can be scheduled
    @Test
    public void testScheduleShift() {
        Employee employee = new Employee();
        ZonedDateTime date = ZonedDateTime.of(2013, 6, 15, 9,
                0, 0, 0, ZoneOffset.UTC);
        when(eventRepository.save(any(Shift.class))).thenReturn(new Shift());
        when(calendarRepository.save(employee.getCalendar())).thenReturn(new Calendar());
        Shift result = scheduler.scheduleShift(employee, date, "Canterlot", 8);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(employee, result.getEmployee());
        Assertions.assertEquals(LocalDate.parse("2013-06-15"), result.getDate());
        Assertions.assertEquals("Canterlot", result.getLocation());
        Assertions.assertEquals(8, result.getHours());
    }

    @Test
    public void testScheduleShiftNull() {
        Employee employee = new Employee();
        employee.setSchedulable(false);
        ZonedDateTime date = ZonedDateTime.of(2013, 6, 15, 9,
                0, 0, 0, ZoneOffset.UTC);
        when(eventRepository.save(any(Shift.class))).thenReturn(new Shift());
        when(calendarRepository.save(employee.getCalendar())).thenReturn(new Calendar());
        Shift result = scheduler.scheduleShift(employee, date, "Canterlot", 8);

        Assertions.assertNull(result);
    }

    @Test
    public void testScheduleMeeting(){
        Employee host = new Employee();
        List<Employee> participants = new ArrayList<>();
        participants.add(new Employee());
        ZonedDateTime date = ZonedDateTime.of(2013, 6, 15, 9,
                0, 0, 0, ZoneOffset.UTC);
        when(eventRepository.save(any(Meeting.class))).thenReturn(new Meeting());
        when(calendarRepository.save(any(Calendar.class))).thenReturn(new Calendar());
        Meeting result = scheduler.scheduleMeeting(host, participants, date,
                "ABC Meeting", "123 Room", 2);

        Assertions.assertNotNull(result);
        verify(eventRepository, times(1)).save(any(Event.class));
        verify(calendarRepository, times(1)).save(host.getCalendar());
        verify(calendarRepository, times(participants.size())).save(host.getCalendar());
        Assertions.assertEquals(LocalDate.parse("2013-06-15"), result.getDate());
        Assertions.assertEquals("ABC Meeting", result.getName());
        Assertions.assertEquals("123 Room", result.getLocation());
        Assertions.assertEquals(2, result.getHours());
    }

    // Test if the
    @Test
    public void testScheduleMeetingNull1(){
        Employee host = new Employee();
        host.setSchedulable(false);
        List<Employee> participants = new ArrayList<>();
        participants.add(new Employee());
        ZonedDateTime date = ZonedDateTime.of(2013, 6, 15, 9,
                0, 0, 0, ZoneOffset.UTC);
        when(eventRepository.save(any(Meeting.class))).thenReturn(new Meeting());
        when(calendarRepository.save(any(Calendar.class))).thenReturn(new Calendar());
        Meeting result = scheduler.scheduleMeeting(host, participants, date,
                "ABC Meeting", "123 Room", 2);

        Assertions.assertNull(result);
    }

    //Test if one of participants is not schedulable.
    @Test
    public void testScheduleMeetingNul2(){
        Employee host = new Employee();
        List<Employee> participants = new ArrayList<>();
        participants.add(new Employee());
        participants.get(0).setSchedulable(false);
        ZonedDateTime date = ZonedDateTime.of(2013, 6, 15, 9,
                0, 0, 0, ZoneOffset.UTC);
        when(eventRepository.save(any(Meeting.class))).thenReturn(new Meeting());
        when(calendarRepository.save(any(Calendar.class))).thenReturn(new Calendar());
        Meeting result = scheduler.scheduleMeeting(host, participants, date,
                "ABC Meeting", "123 Room", 2);

        Assertions.assertNull(result);
    }

    // Test that only a single shift can be scheduled for a given day
    @Test
    public void testContiguous() {
        Employee employee = new Employee();
        ZonedDateTime date1 = ZonedDateTime.of(2013, 6, 15, 9,
                0, 0, 0, ZoneOffset.UTC);
        ZonedDateTime date2 = ZonedDateTime.of(2013, 6, 15, 21,
                0, 0, 0, ZoneOffset.UTC);
        when(eventRepository.save(any(Shift.class))).thenReturn(new Shift());
        when(calendarRepository.save(employee.getCalendar())).thenReturn(new Calendar());

        Assertions.assertNotNull(scheduler.scheduleShift(employee, date1, "Canterlot", 8));
        Assertions.assertNull(scheduler.scheduleShift(employee, date2, "Canterlot", 8));
    }

    // Test that the maximum hours may be attained
    @Test
    public void testMaxHoursAttained() {
        Employee employee = new Employee(); // MaximumHourPerWeek is 8 hours.
        ZonedDateTime date1 = ZonedDateTime.of(2013, 6, 15, 9,
                0, 0, 0, ZoneOffset.UTC);
        ZonedDateTime date2 = ZonedDateTime.of(2013, 6, 16, 9,
                0, 0, 0, ZoneOffset.UTC);
        when(eventRepository.save(any(Shift.class))).thenReturn(new Shift());
        when(calendarRepository.save(employee.getCalendar())).thenReturn(new Calendar());

        Assertions.assertNotNull(scheduler.scheduleShift(employee, date1, "Canterlot", 7));
        Assertions.assertNull(scheduler.scheduleShift(employee, date2, "Canterlot", 8));
        Assertions.assertNotNull(scheduler.scheduleShift(employee, date2, "Canterlot", 1));
    }

    // Test that only a maximum number of hours may be scheduled per week
    @Test
    public void testMaxHoursExceeded() {
        Employee employee = new Employee(); // MaximumHourPerWeek is 8 hours.
        ZonedDateTime date1 = ZonedDateTime.of(2013, 6, 15, 9,
                0, 0, 0, ZoneOffset.UTC);
        ZonedDateTime date2 = ZonedDateTime.of(2013, 6, 16, 9,
                0, 0, 0, ZoneOffset.UTC);
        when(eventRepository.save(any(Shift.class))).thenReturn(new Shift());
        when(calendarRepository.save(employee.getCalendar())).thenReturn(new Calendar());

        Assertions.assertNotNull(scheduler.scheduleShift(employee, date1, "Canterlot", 8));
        Assertions.assertNull(scheduler.scheduleShift(employee, date2, "Canterlot", 8));
    }
}
