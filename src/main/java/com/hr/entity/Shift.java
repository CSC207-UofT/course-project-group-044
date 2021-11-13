package com.hr.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.Duration;
import java.time.Instant;

@Entity
public class Shift extends Event {
	@OneToOne
	private Employee employee;

	public Shift(Employee employee, Instant start, Duration duration, String location) {
		super(start, duration, "Shift", location);
		this.employee = employee;
	}

	public Shift() {

	}
}
