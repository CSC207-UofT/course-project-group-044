package Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.*;
import java.util.List;

@Entity
public class Employee {
    @Id
    private final int id;

    @Column
    private final String name;

    @Column
    private final Calendar calendar;

    @Column
    private int salary;

    @Column
    private int maxHoursPerWeek;

    @Column
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

    public Employee() {
        this.name = "";
        this.id = 0;
        this.salary = 0;
        this.maxHoursPerWeek = 8;
        this.schedulable = true;
        this.calendar = new Calendar();
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
