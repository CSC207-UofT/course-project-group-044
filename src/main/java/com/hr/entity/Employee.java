package com.hr.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.ZonedDateTime;

@Entity
/**
 * An entity class that stores the information of Employee.
 *
 * @param id               the specific id of this Employee.
 * @param name             the name of this Employee.
 * @param calendar         the calendar of this Employee.
 * @param salary           the salary of this Employee.
 * @param maxHoursPerWeek  the max hours that this employee can work in a week.
 * @param schedulable      whether this employee can be assigned events.
 * @param hoursPerShift    the number of hours of shift.
 */
public class Employee {
    @Id
    private int id;

    @Column
    private String name;

    @OneToOne
    private Calendar calendar;

    @Column
    // Hourly Wage of the Employee
    private int salary;

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    @Column
    private int maxHoursPerWeek;

    @Column
    private boolean schedulable;

    @Column
    private int hoursPerShift;
    /**
     * Constructor to create an instance of Employee that has complete information .
     */

    public Employee(String name, int id, Calendar calendar,
                    int salary, int maxHoursPerWeek, int hoursPerShift,
                    boolean schedulable) {

        this.name = name;
        this.id = id;
        this.salary = salary;
        this.maxHoursPerWeek = maxHoursPerWeek;
        this.hoursPerShift = hoursPerShift;
        this.schedulable = schedulable;
        this.calendar = calendar;
    }

    public void setHoursPerShift(int hoursPerShift) {
        this.hoursPerShift = hoursPerShift;
    }

    /**
     * Constructor to create an blank instance of Employee that sets all information default.
     */

    public Employee() {
        this.name = "";
        this.id = 0;
        this.salary = 0;
        this.maxHoursPerWeek = 8;
        this.schedulable = true;
        this.hoursPerShift = 0;
        this.calendar = new Calendar();
    }

    // getters for every attributes above
    /**
     * Gets the name of this Employee.
     *
     * @return A string that is the name of the Employee.
     */
    public String getName() { return this.name; }
    /**
     * Gets the id of this Employee.
     *
     * @return An int that is the id of the Employee.
     */
    public int getId() { return this.id; }
    /**
     * Gets the number of hours in shift of this Employee.
     *
     * @return An int that is the number of hours in shift.
     */
    public int getHoursPerShift() { return this.hoursPerShift; }
    /**
     * Gets the salary of this Employee.
     *
     * @return An int that is the salary of the Employee.
     */
    public int getSalary() { return this.salary; }
    /**
     * Gets the max hours that this Employee can work in a week.
     *
     * @return An int that is the max hours that this Employee can work in a week.
     */
    public int getMaxHoursPerWeek() { return this.maxHoursPerWeek; }
    /**
     * Gets whether this Employee can be assigned Event.
     *
     * @return A boolean that indicates whether this Employee is schedulable.
     */
    public boolean getSchedulable() { return this.schedulable; }
    /**
     * Gets the Calendar of this Employee.
     *
     * @return A Calendar of this Employee.
     */
    public Calendar getCalendar() { return calendar; }
    /**
     * Gets the left working hours of this Employee.
     *
     * @param week a ZonedDateTime that represents time of week.
     * @return A long that is the rest working hours of this Employee.
     */
    public long getUnscheduledHours(ZonedDateTime week) {
        return maxHoursPerWeek - calendar.hoursInWeek(week);
    }
    // setters

    /**
     * Sets the new salary to replace original salary of this Employee.
     */
    public void setSalary(int new_sal) { this.salary = new_sal; }
    /**
     * Sets the new max hours that this employee can work in a week.
     */
    public void setMaxHoursPerWeek(int new_hour) { this.maxHoursPerWeek = new_hour; }
    /**
     * Sets the boolean that indicates whether this Employee can be assigned Event.
     */
    public void setSchedulable(boolean new_bool) { this.schedulable = new_bool; }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name){this.name = name;}
}
