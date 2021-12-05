package com.hr.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
/**
 * An entity class that stores the information of Calendar which consists of manny Events.
 *
 * @param events  the list of Events.
 * @see Event
 */
public class Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID calendarID;

//    @ElementCollection
//    private List<Integer> eventsID;

    @ManyToMany
    private List<Event> events;

    public UUID getCalendarID() {
        return calendarID;
    }

    public void setCalendarID(UUID calendarID) {
        this.calendarID = calendarID;
    }
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
     * @param Date/time to check
     * @return Count
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
