import com.hr.entity.Employee;
import com.hr.entity.Event;
import com.hr.repository.EmployeeRepository;
import com.hr.repository.EventRepository;
import com.hr.service.impl.EmployeeModifierImpl;
import com.hr.service.impl.EventServiceImpl;
import com.hr.service.impl.SchedulerImpl;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class EventServiceImplTest {
    private Employee employee;
    private EmployeeModifierImpl mgr;
    private long oneHour = 1;

    @InjectMocks
    private EventServiceImpl eventService;

    @Mock
    private EventRepository eventRepository;

    @Before
    public void setUp() {
        SchedulerImpl scheduler = new SchedulerImpl();
        employee = mgr.hireEmployee("Sunset Shimmer", 1, 20, 20, 4);
        ZonedDateTime date = ZonedDateTime.of(2013, 6, 15, 9, 0, 0, 0, ZoneOffset.UTC);
        Instant start = date.toInstant();
        Duration duration = Duration.ofHours(oneHour);
        Event event = new Event(start, duration, "Jason", "BA");
        scheduler.scheduleShift(employee, date, "Canterlot", 8);

        given(eventRepository.findAll()).willReturn(Arrays.asList(event));
    }

    @Test
    public void testGetEventsInSameDate(){
        List<Event> temp = eventService.getEventsInSameDate("2013-06-15");

        Assertions.assertEquals(temp.size(), 1);
    }
}
