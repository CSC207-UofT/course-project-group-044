package com.hr.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Entity
public class Meeting extends Event {
    @OneToOne
    private Employee holder;

    @OneToMany
    private List<Employee> participants;

	public Meeting(Employee holder, List<Employee> participants, Instant start,
                   Duration duration, String name, String location) {
        super(start, duration, name, location);
        this.holder = holder;
        this.participants = participants;
    }

    public Meeting() {

    }
}
