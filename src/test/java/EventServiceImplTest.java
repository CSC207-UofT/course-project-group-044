import com.hr.entity.Employee;
import com.hr.entity.Event;
import com.hr.repository.CalendarRepository;
import com.hr.repository.EventRepository;
import com.hr.service.impl.EmployeeModifierImpl;
import com.hr.service.impl.EventServiceImpl;
import com.hr.service.impl.SchedulerImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EventServiceImplTest {

    @InjectMocks
    private static EventServiceImpl eventService;

    @BeforeAll
    static void setUp() {
//        SchedulerImpl scheduler = new SchedulerImpl();
//        EmployeeModifierImpl mgr = new EmployeeModifierImpl();
//        Employee employee = mgr.hireEmployee("Sunset Shimmer", 1, 20, 20, 4);
        ZonedDateTime date = ZonedDateTime.of(2013, 6, 15, 9, 0, 0, 0, ZoneOffset.UTC);
        Instant start = date.toInstant();
        long oneHour = 1;
        Duration duration = Duration.ofHours(oneHour);
        Event event = new Event(start, duration, "Jason", "BA");
//        scheduler.scheduleShift(employee, date, "Canterlot", 8);

        EventRepository eventRepository = mock(EventRepository.class);
        when(eventRepository.findAll()).thenReturn(Arrays.asList(event));
    }

    @Test
    void testGetEventsInSameDate() {
        List<Event> temp = eventService.getEventsInSameDate("2013-06-15");

        Assertions.assertEquals(1, temp.size());
    }
}
