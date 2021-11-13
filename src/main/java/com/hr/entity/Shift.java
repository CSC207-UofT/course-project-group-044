package com.hr.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.Duration;
import java.time.Instant;

//@Entity
public class Shift extends Event {
	@Column
	private Employee employee;

	public Shift(Employee employee, Instant start, Duration duration, String location) {
		super(start, duration, "Shift", location);
		this.employee = employee;
	}
}
