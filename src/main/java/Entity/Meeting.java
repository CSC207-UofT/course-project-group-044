package Entity;

import Entity.Event;
import Entity.Employee;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Entity
public class Meeting extends Event {
    @Column
    private Employee holder;

    @Column
    private List<Employee> participants;

	public Meeting(Employee holder, List<Employee> participants, Instant start,
                   Duration duration, String name, String location) {
        super(start, duration, name, location);
        this.holder = holder;
        this.participants = participants;
    }
}
