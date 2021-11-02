package Entity;

import java.time.*;
import java.time.temporal.ChronoField;

public abstract class Event {
	private Instant start;
	private Duration duration;
	private String name;
	private String location;

	public Event(Instant start, Duration duration, String name, String location) {
        this.start = start;
        this.duration = duration;
        this.name = name;
        this.location = location;
	}

    /**
     * Get the date of a shift in a given timezone
     *
     * @return Local date
     */
    public LocalDate getDate(ZoneId zone) {
            return start.atZone(zone).toLocalDate();
    }

    /**
     * Test if the event occur during the same week as a given date/time.
     *
     * @return True if so.
     */
    public boolean isSameWeek(ZonedDateTime ref) {
        ZonedDateTime comp = ZonedDateTime.ofInstant(start, ZoneOffset.UTC); // TODO

        int week_1 = ref.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
        int week_2 = comp.get(ChronoField.ALIGNED_WEEK_OF_YEAR);

        return ref.getYear() == comp.getYear() && week_1 == week_2;
    }

    /**
     * Get the number of hours spanned by the event
     *
     * @return Hours
     */
    public long getHours() {
        return duration.toHours();
    }

}
