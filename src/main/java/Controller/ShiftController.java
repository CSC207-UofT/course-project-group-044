package Controller;

import Entity.Employee;
import Service.Scheduler;

import java.time.ZonedDateTime;
import java.util.List;

public class ShiftController {
    Scheduler scheduler;

    public ShiftController(){}

    public void addShift(Employee employee, ZonedDateTime date, String location, int hours){
        scheduler.scheduleShift(employee, date, location, hours);
    }

    public void addMeeting(Employee host, List<Employee> participants, ZonedDateTime date,
                           String name, String location, int hours){
        scheduler.scheduleMeeting(host, participants, date, name, location, hours);
    }

    public void removeShift(Employee employee, ZonedDateTime date, String location, int hours){
        scheduler.cancelShift(employee, date, location, hours);
    }

    public void removeMeeting(Employee host, List<Employee> participants, ZonedDateTime date,
                              String name, String location, int hours){
        scheduler.cancelMeeting(host, participants, date, location, name, hours);
    }
}
