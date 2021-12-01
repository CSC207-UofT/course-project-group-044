package serviceTest;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.hr.entity.Event;
import com.hr.repository.EventRepository;
import com.hr.service.impl.EventServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventServiceImplTest {

    @InjectMocks
    private static EventServiceImpl eventService;

    @Mock
    private EventRepository eventRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        eventService = new EventServiceImpl(eventRepository);
    }

    @Test
    void testDeleteEvents(){
        Event event = new Event(Instant.parse("2013-06-15T09:00:00.00Z"), Duration.ofHours(1),
                "Jason", "BA");
        when(eventRepository.findAll()).thenReturn(List.of(event));
        boolean result = eventService.deleteEvents("2013-06-15");

        verify(eventRepository).delete(event);
        Assertions.assertTrue(result);

    }

    @Test
    void testDeleteEventsNullEvent(){
        Event event = new Event(Instant.parse("2013-06-15T09:00:00.00Z"), Duration.ofHours(1),
                "Jason", "BA");
        when(eventRepository.findAll()).thenReturn(List.of(event));
        boolean result = eventService.deleteEvents("2013-06-16");

        verify(eventRepository, times(0)).delete(event);
        Assertions.assertFalse(result);
    }

    @Test
    void testGetEventsInSameDate() {
        Event event = new Event(Instant.parse("2013-06-15T09:00:00.00Z"), Duration.ofHours(1),
                "Jason", "BA");
        List<Event> expect = new ArrayList<>();
        expect.add(event);
        when(eventRepository.findAll()).thenReturn(List.of(event));
        List<Event> temp = eventService.getEventsInSameDate("2013-06-15");
        Assertions.assertEquals(1, temp.size());
        Assertions.assertEquals(expect, temp);
    }
}
