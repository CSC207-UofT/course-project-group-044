package entity;

import java.time.*;
import java.util.List;
import java.util.ArrayList;

public class Calendar {

    private List<Event> events;

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
