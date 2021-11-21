package com.hr.service;

import com.hr.entity.Employee;
import com.hr.entity.Meeting;
import com.hr.entity.Shift;
import org.sat4j.specs.ContradictionException;
import java.time.ZonedDateTime;
import java.util.List;

public interface Scheduler {

    boolean schedulable(Employee employee, ZonedDateTime start, int hours);

    Shift shiftFinder(Employee employee, ZonedDateTime date, String location, int hours);

    Meeting meetingFinder(Employee host, List<Employee> participants, ZonedDateTime date,
                                 String name,  String location, int hours);

    Shift scheduleShift(Employee employee, ZonedDateTime date, String location, int hours);

    Meeting scheduleMeeting (Employee host, List<Employee> participants, ZonedDateTime date,
                                    String name,  String location, int hours);

    Shift cancelShift(Employee employee, ZonedDateTime date, String location, int hours);

    Meeting cancelMeeting(Employee host, List<Employee> participants, ZonedDateTime date,
                                 String location, String name, int hours);

    int hoursPerDay();

    int countVariables();

    int var(int employee, int day, int hour);

    void constrainMaxHours() throws ContradictionException;

    void constrainMinSimultaneous() throws ContradictionException;

    void constrainContiguousShifts() throws ContradictionException;

    boolean scheduleWeek(ZonedDateTime base);
}