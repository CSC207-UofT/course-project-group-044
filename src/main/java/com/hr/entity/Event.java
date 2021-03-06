package com.hr.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
    /**
     * Gets the start time of the event.
     *
     * @return  start time of this Event.
     */
    public Instant getStart() {
        return start;
    }

    /**
     * Gets the string of start time of the event.
     *
     * @return  string of start time of this Event.
     */
    public String getStartString() {
        return start.toString();
    }

    /**
     * Gets the duration of the event.
     *
     * @return  duration of this Event.
     */
    public String getDurationString() {
        return duration.toString();
    }

    /**
     * Gets the name of the event.
     *
     * @return  name of this Event.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the location of the event.
     *
     * @return  location of this Event.
     */
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
