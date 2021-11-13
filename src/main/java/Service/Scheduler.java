package Service;

import Entity.*;

import java.time.*;
import java.util.List;
import java.util.ListIterator;

import org.sat4j.pb.SolverFactory;
import org.sat4j.pb.IPBSolver;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.TimeoutException;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IVecInt;
import org.sat4j.core.VecInt;

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
    private IPBSolver solver;
    private List<Employee> employees;

    // TODO: Should these constants be configurable for more flexibility?
    private final int s_l = 0;
    private final int s_h = 8;
    private final int daysOfWeek = 7;

    public Scheduler() {
    }

    public Scheduler(List<Employee> employees) {
        this.employees = employees;

        solver = SolverFactory.newDefault();
        solver.setTimeout(10);
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

       // Otherwise, no other constraints to check here
       return true;
    }

    public Shift shiftFinder(Employee employee, ZonedDateTime date, String location, int hours){
        List<Event> events = employee.getCalendar().getEvents();
        Duration duration = Duration.ofHours(hours);
        Event target_shift = new Shift(employee, date.toInstant(), duration, location);
        for (Event e:events){
            if (e == target_shift){
                return (Shift) e;
            }
        }
        return null;
    }

    public Meeting meetingFinder(Employee host, List<Employee> participants, ZonedDateTime date,
                               String name,  String location, int hours){
        List<Event> events = host.getCalendar().getEvents();
        Duration duration = Duration.ofHours(hours);
        Event target_meeting = new Meeting(host, participants, date.toInstant(), duration, name, location);
        for (Event e:events){
            if (e == target_meeting){
                return (Meeting) e;
            }
        }
        return null;
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

    public Meeting scheduleMeeting (Employee host, List<Employee> participants, ZonedDateTime date,
                                    String name,  String location, int hours) {
        // Check if legal to schedule
        if (!schedulable(host, date, hours))
            return null;
        for (Employee e:participants){
            if (! schedulable(e, date, hours))
                return null;
        }

        // It is! Create the Shift.
        Duration duration = Duration.ofHours(hours);
        Meeting meeting = new Meeting(host, participants, date.toInstant(), duration, name, location);

        host.getCalendar().addEvent(meeting);
        for (Employee e:participants){
            e.getCalendar().addEvent(meeting);
        }
        return meeting;
    }

    public Shift cancelShift(Employee employee, ZonedDateTime date, String location, int hours){
        Shift target = shiftFinder(employee, date, location, hours);
        if (target != null){
            employee.getCalendar().getEvents().remove(target);
            return target;
        }
        return null;
    }

    public Meeting cancelMeeting(Employee host, List<Employee> participants, ZonedDateTime date,
                                 String location, String name, int hours){
        Meeting target = meetingFinder(host, participants, date, location, name, hours);
        if (target != null){
            host.getCalendar().getEvents().remove(target);
            for (Employee e:participants){
                e.getCalendar().getEvents().remove(target);
            }
            return target;
        }
        return null;
    }

    private int hoursPerDay() {
        return s_h - s_l;
    }

    private int countVariables() {
        return employees.size() * daysOfWeek * hoursPerDay();
    }

    private int var(int employee, int day, int hour) {
        // All quantities are flattened to be zero indexed
        int h = hour - s_l;
        int i = employee - 1;
        int d = day - 1;

        // Ensure maxes are satisfied
        assert(0 <= h && h < hoursPerDay());
        assert(0 <= d && d < daysOfWeek);
        assert(0 <= i && i < employees.size());

        // 3D index (i, d, h) flattened to 1D
        int idx = h + hoursPerDay() * (d + daysOfWeek * i);

        // Solver wants 1-indexed, so add 1
        assert(idx >= 0 && idx < countVariables());
        return idx + 1;
    }

    private void constrainMaxHours() throws ContradictionException {
        // 1. Maximum number of hours worked per employee per week
        // 1. For each employee i, sum_{d = 1}^7 sum_{h = s_l}^{s_h} x_idh <=
        //    maximum # of hours employee i can work per week.
        ListIterator<Employee> it = employees.listIterator();

        while (it.hasNext()) {
            int i = it.nextIndex() + 1;
            IVecInt literals = new VecInt();

            for (int d = 1; d <= 7; ++d) {
                for (int h = s_l; h < s_h; ++h) {
                    literals = literals.push(var(i, d, h));
                }
            }

            solver.addAtMost(literals, it.next().getMaxHoursPerWeek());
        }
    }

    private void constrainMinSimultaneous() throws ContradictionException {
        // 2. Minimum number of employees working simultaneously
        // 2. For each h, d: sum_{i = 1}^N x_i >= minimum # of employees simultaneously
        for (int d = 1; d <= 7; ++d) {
            for (int h = s_l; h < s_h; ++h) {
                IVecInt literals = new VecInt();
                final int minimum = 1; // TODO: configurable

                for (int i = 1; i <= employees.size(); ++i) {
                    literals.push(var(i, d, h));
                }

                solver.addAtLeast(literals, minimum);
            }
        }
    }

    private void constrainContiguousShifts() throws ContradictionException {
        // 4. Shifts must be contiguous.
        // 4. For each employee i, each day d, end hour h, and start hour s
        // such that s_l <= s < h - 1, NOT(x_ids) OR NOT(x_idh) OR x_id{s + 1}
        for (int i = 1; i <= employees.size(); ++i) {
            for (int d = 1; d <= 7; ++d) {
                for (int h = s_l; h < s_h; ++h) {
                    for (int s = 0; s < h - 1; ++s) {
                        int[] literals = {
                            -var(i, d, s), -var(i, d, h), var(i, d, s + 1)
                        };

                        solver.addClause(new VecInt(literals));
                    }
                }
            }
        }
    }

    /**
     * Automatically schedule a week
     *
     * @param base The week to schedule
     * @return Whether scheduling was successful.
     */

    public boolean scheduleWeek(ZonedDateTime base) {
        // Create all variables
        solver.newVar(countVariables());

        // Define constraints
        try {
            constrainMaxHours();
            constrainMinSimultaneous();
            constrainContiguousShifts();
        } catch(ContradictionException exp) {
            return false;
        }

        // Now solve
        IProblem problem = solver;

        try {
            if (!problem.isSatisfiable()) {
                return false;
            }
        } catch (TimeoutException exp) {
            return false;
        }

        ListIterator<Employee> it = employees.listIterator();
        while (it.hasNext()) {
            int i = it.nextIndex() + 1;
            Employee employee = it.next();

            // Get the shift for each day and schedule
            for (int d = 1; d <= 7; ++d) {
                int firstHour = -1;
                int lastHour = s_h;

                // Combine subsequent shifts
                for (int h = s_l; h < s_h; ++h) {
                    boolean scheduled = problem.model(var(i, d, h));

                    if (scheduled && firstHour < 0) {
                        firstHour = h;
                    } else if (!scheduled && firstHour >= 0 && lastHour == s_h) {
                        lastHour = h;
                    }
                }

                if (firstHour >= 0) {
                    int hours = lastHour - firstHour;
                    ZonedDateTime start = base.plusDays(d - 1).plusHours(firstHour);
                    Shift shift = scheduleShift(employee, start, "Canterlot", hours);

                    // Solver invariant
                    System.out.println(employee.toString() + start.toString());
                    assert(shift != null);
                }
            }
        }

        return true;
    }
}
