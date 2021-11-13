package Entity;

import Entity.Event;

import javax.persistence.Entity;
import java.time.*;
import java.util.List;
import java.util.ArrayList;

@Entity
/**
 * An entity class that stores the information of Calendar which consists of manny Events.
 *
 * @param events  the list of Events.
 * @see Event
 */
public class Calendar {

    private List<Event> events;

    /**
     * Gets the list of Events in this Calendar.
     *
     * @return An list of Events in this Calendar.
     */
    public List<Event> getEvents() {
        return events;
    }

    /**
     * Sets the list of Events as new events attribute for this Calendar.
     */
    public void setEvents(List<Event> events) {
        this.events = events;
    }

    /**
     * Constructor to create an instance of Calendar with an empty list of Employees.
     */
    public Calendar() {
        this.events = new ArrayList<>();
    }

    /**
     * Adds an Event to the list of Events of this Calendar.
     */
    public void addEvent(Event event) {
        this.events.add(event);
    }

    /**
     * Get the number of events on a given day.
     *
     * @param argument Date/time to check
     * @return the total number of Events on the given day.
     */
    public long eventsOnDay(ZonedDateTime argument) {
        LocalDate ref = argument.toLocalDate();
        ZoneId zone = argument.getZone();

        return events.stream().filter(c -> c.getDate(zone).equals(ref)).count();
    }

    /**
     * Get the number of hours scheduled in a given week.
     *
     * @param argument Date/time to check
     * @return the total number of hours on the given day.
     */
    public long hoursInWeek(ZonedDateTime argument) {
        return events.stream().filter(c -> c.isSameWeek(argument))
                     .mapToLong(c -> c.getHours()).sum();
    }

}
