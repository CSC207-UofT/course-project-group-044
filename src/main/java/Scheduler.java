import Entity.Calendar;
import Entity.Employee;
import Entity.Shift;

import java.time.*;

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

    public Scheduler() {
    }

    public Scheduler(Calendar calendar) {
        this.calendar = calendar;
    }

    /**
     * Determine whether it is valid to schedule a shift with the given
     * configuration.
     *
     * @param employee Entity.Employee to check scheduling.
     * @param start Start of shift
     * @param hours Length of shift
     * @return Whether it is valid to schedule.
     */

    private boolean schedulable(Employee employee, ZonedDateTime start, int hours) {
       // Maximum number of hours worked per employee per week
       if (hours > employee.getUnscheduledHours(start)) {
           return false;
       }

       // Shifts must be contiguous => only one shift per day
       if (employee.getCalendar().eventsOnDay(start) >= 1) {
           return false;
       }

       // Employees must not be scheduled while on leave.
       // TODO: leave tracking

       // Otherwise, no other constraints to check here
       return true;
    }

    /**
     * Attempt to schedule a single shift.
     *
     * @param employee Entity.Employee to schedule
     * @param date Date and time to schedule
     * @param location Location of the shift
     * @return The scheduled event, or null if no Shift could be scheduled.
     */
    public Shift scheduleShift(Employee employee, ZonedDateTime date, String location, int hours) {
        // Check if legal to schedule
        if (!schedulable(employee, date, hours))
            return null;

        // It is! Create the Shift.
        Duration duration = Duration.ofHours(hours);
        Shift shift = new Shift(employee, date.toInstant(), duration, location);

        employee.getCalendar().addEvent(shift);
        return shift;
    }
}
