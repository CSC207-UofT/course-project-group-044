package Service;

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
 * Scheduling uses an off-the-shelf SAT solver. To keep the computational
 * complexity tractable, we schedule at the granularity of a single week for a
 * single location. First, we define the boolean variables. Enumerate the N
 * employees that are considered for scheduling. For employee i, day of week d,
 * start hour h, define the boolean variable x_idh as whether the employee is
 * scheduled for an hour beginning at the given time. Shift starts are bounded
 * to a particular set of hours [s_l, s_h). Since 1 < i <= N, 1 <= d < 7, s_l
 * <= h < s_h, we have defined N (7) (s_h - s_l) < 168 N boolean variables.
 * Notice that an efficient solver is needed even in this simple model: there
 * are up to 2^168N combinations to search!
 *
 * Next, express the hard constraints in terms of the boolean variables.
 *
 * 1. For each employee i, sum_{d = 1}^7 sum_{h = s_l}^{s_h} x_idh <=
 *    maximum # of hours employee i can work per week.
 *
 * 2. For each h, d: sum_{i = 1}^N x_i >= minimum # of employees simultaneously
 *
 * 3. For each employee i, each day d they are on leave, and each hour h of
 *    that day, NOT(x_idh)
 *
 * 4. For each employee i, each day d, end hour h, and start hour s such that
 *    s_l <= s < h - 1, x_ids AND x_idh IMPLIES x_id{s + 1}. Taken together,
 *    these constraints asserts shifts are contiguous each day. Expanding out
 *    the definition of IMPLIES and applying De Morgan's Law, this constraint
 *    may be written in conjunctive normal form as NOT(x_ids) OR NOT(x_idh) OR
 *    NOT(x_ID{s + 1}).
 *
 * The soft constraints will be used to guide optimization.
 *
 * 1. For each employee i, sum_{d = 1}^7 sum_{h = s_l}^{s_h} x_idh >= minimum #
 *    of hours
 *
 * 2. For each h, d: sum_{i = 1}^N x_i <= maximum # of employees
 *
 * Combining these soft constraints gives an optimization function. Notice hard
 * constraints 3 and 4 are purely propositional logic, while constraints 1 and
 * 2 are pseudo-boolean constraints. SAT4J can handle both kinds of
 * constraints, reducing the amount of set up work we have.
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
        employee.setSchedulable(false);

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
