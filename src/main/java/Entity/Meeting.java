package Entity;

import Entity.Event;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class Meeting extends Event {
    private Employee holder;
    private List<Employee> participants;

	public Meeting(Employee holder, List<Employee> participants, Instant start,
                   Duration duration, String name, String location) {
        super(start, duration, name, location);
        this.holder = holder;
        this.participants = participants;
    }
}
