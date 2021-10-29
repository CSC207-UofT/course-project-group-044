import java.time.*;
import java.util.List;

public class Employee {
    private final String name;
    private final int id;
    private final Calendar calendar;
    private int salary;
    private int maxHoursPerWeek;
    private boolean schedulable;

    public Employee(String name, int id, Calendar calendar,
                    int salary, int maxHoursPerWeek, boolean schedulable) {

        this.name = name;
        this.id = id;
        this.salary = salary;
        this.maxHoursPerWeek = maxHoursPerWeek;
        this.schedulable = schedulable;
        this.calendar = calendar;
    }

    // getters for every attributes above
    public String getName() { return this.name; }

    public int getId() { return this.id; }

    public int getSalary() { return this.salary; }

    public int getMaxHoursPerWeek() { return this.maxHoursPerWeek; }

    public boolean getSchedulable() { return this.schedulable; }

    public Calendar getCalendar() { return calendar; }

    // setters
    public void setSalary(int new_sal) { this.salary = new_sal; }

    public void setMaxHoursPerWeek(int new_hour) { this.maxHoursPerWeek = new_hour; }

    public void setSchedulable(boolean new_bool) { this.schedulable = new_bool; }

    public long getUnscheduledHours(ZonedDateTime week) {
        return maxHoursPerWeek - calendar.hoursInWeek(week);
    }
}
