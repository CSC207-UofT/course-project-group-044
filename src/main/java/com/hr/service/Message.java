package com.hr.service;

import com.hr.entity.Calendar;
import com.hr.entity.Employee;
import com.hr.entity.Event;

import java.util.List;

public class Message {
    private Employee employee;
    private Event event;
    private Calendar calendar;
    private String s;
    private Employee host;
    private List<Employee> participants;

    public Message(){}

    // for calling the employee
    public Message(String s, Employee employee){
        this.s = s;
        this.employee = employee;
    }

    //for calling the shift
    public Message(String s, Employee employee, Event event){
        this.s = s;
        this.employee = employee;
        this.event = event;
    }

    //for calling the meeting
    public Message(String s, Employee host, List<Employee> participants, Event event){
        this.s = s;
        this.host = host;
        this.participants = participants;
        this.event = event;
    }

    public String getS() {
        return s;
    }

    public Employee getEmployee(){
        return employee;
    }

    public Event getEvent(){
        return event;
    }

    public Employee getHost(){
        return host;
    }

    public List<Employee> getParticipants(){
        return participants;
    }
}
