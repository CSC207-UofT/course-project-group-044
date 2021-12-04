package com.hr.entity;

import javax.persistence.*;
import java.time.*;
import java.time.temporal.ChronoField;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
/**
 * An abstract entity class that stores the information of Event with two subclasses - Meeting & Shift.
 *
 * @param start        the start time of this Event.
 * @param duration     the amount of time that from Event start to end.
 * @param name         the name of this Event.
 * @param location     the specific address of this Event.
 * @see Meeting
 * @see Shift
 */
public class Event {
    @Id
	private Instant start;

    @Column
	private Duration duration;

    @Column
	private String name;

    @Column
	private String location;
    /**
     * Constructor to create an instance of Event that has full complete information .
     */
	public Event(Instant start, Duration duration, String name, String location) {
        this.start = start;
        this.duration = duration;
        this.name = name;
        this.location = location;
	}

    public Event() {

    }
    public Instant getStart() {
        return start;
    }

    public String getStartString() {
        return start.toString();
    }

    public String getDurationString() {
        return duration.toString();
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    /**
     * Gets the date of a shift in a given timezone.
     *
     * @param zone a ZoneId represents local time zone.
     * @return Local start time of this Event.
     */
    public LocalDate getDate(ZoneId zone) {
            return start.atZone(zone).toLocalDate();
    }

    /**
     * Tests if the Event occur during the same week as a given date/time.
     * Get the date in the default timezone
     *
     * @return Local date
     */
    public LocalDate getDate() {
        return getDate(ZoneOffset.UTC);
    }

    /**
     * Test if the event occur during the same week as a given date/time.
     *
     * @return True if so, vice versa.
     */
    public boolean isSameWeek(ZonedDateTime ref) {
        ZonedDateTime comp = ZonedDateTime.ofInstant(start, ZoneOffset.UTC); // TODO

        int week_1 = ref.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
        int week_2 = comp.get(ChronoField.ALIGNED_WEEK_OF_YEAR);

        return ref.getYear() == comp.getYear() && week_1 == week_2;
    }

    /**
     * Gets the number of hours spanned by this Event.
     *
     * @return the number of hours of entire eEvent.
     */
    public long getHours() {
        return duration.toHours();
    }

}
