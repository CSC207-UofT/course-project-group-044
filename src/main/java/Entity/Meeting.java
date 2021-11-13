package Entity;

import Entity.Event;
import Entity.Employee;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Entity
/**
 * An entity class that stores the information of Meeting. The superclass is Event.
 *
 * @param holder        the event holder of this Meeting.
 * @param participants  the list of Employees attended this Meeting.
 * @see Event
 */
public class Meeting extends Event {
    @Column
    private Employee holder;

    @Column
    private List<Employee> participants;

    /**
     * Constructor to create an instance of Meeting that has full complete information .
     *
     *  @param start        the start time of this Event.
     *  @param duration     the amount of time that from Event start to end.
     *  @param name         the name of this Event.
     *  @param location     the specific address of this Event.
     */
	public Meeting(Employee holder, List<Employee> participants, Instant start,
                   Duration duration, String name, String location) {
        super(start, duration, name, location);
        this.holder = holder;
        this.participants = participants;
    }
}
