package com.hr.entity;

import javax.persistence.*;
import java.time.*;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

@Entity
public class Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID calendarID;

//    @ElementCollection
//    private List<Integer> eventsID;

    @OneToMany
    private List<Event> events;

    public UUID getCalendarID() {
        return calendarID;
    }

    public void setCalendarID(UUID calendarID) {
        this.calendarID = calendarID;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public Calendar() {
        this.events = new ArrayList<>();
    }

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
     * @param Date/time to check
     * @return Count
     */
    public long hoursInWeek(ZonedDateTime argument) {
        return events.stream().filter(c -> c.isSameWeek(argument))
                     .mapToLong(c -> c.getHours()).sum();
    }

}
