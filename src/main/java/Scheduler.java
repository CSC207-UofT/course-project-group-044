import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Duration;
import java.time.ZoneOffset;

/**
 * The automatic shift scheduler manages the Calendars in the Organization,
 * creating Shift objects to satisfy shift scheduling constraints. We handle
 * this as a discrete problem, with the basic step of scheduling a single
 * Shift, or refusing to schedule any Shifts if scheduling is impossible.
 *
 * Shift scheduling has the following hard constraints which must be satisfied:
 *
 *  1. Maximum number of hours worked per employee per week
 *  2. Minimum number of employees working simultaneously
 *  3. Employees must not be scheduled while on leave.
 *  4. Shifts must be contiguous.
 *
 * There are also the following soft constraints defining a cost function:
 *
 *  1. Minimum number of hours worked per employee per week
 *  2. Maximum number of employees working simultaneously per location
 *
 * For simplicity (this is a 200-level undergraduate project after all),
 * constraint #4 is interpreted as only scheduling a single shift per day.
 * Constraint #2 is interpreted as applying across the entire Organization.
 *
 * The scheduler works locally to minimize the cost function. Globally, this
 * makes it a greedy heuristic solver, which should work well enough.
 */

public class Scheduler {
    public Calendar calendar;

    public Scheduler(Calendar calendar) {
        this.calendar = calendar;
    }

    public void createEvent(Instant start, Event event){
        this.calendar.addEvent(start, event);
    }

}
