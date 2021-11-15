package com.hr.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.Duration;
import java.time.Instant;

@Entity
/**
 * An entity class that stores the information of Shift. The superclass is Event.
 *
 * @param employee the employee that will be assigned with this Shift.
 * @see Event
 */
public class Shift extends Event {
	@OneToOne
	private Employee employee;

	/**
	 * Constructor to create an instance of Shift that has full complete information.
	 *
	 *  @param start        the start time of this Event.
	 *  @param duration     the amount of time that from Event start to end.
	 *  @param location     the specific address of this Event.
	 */

	public Shift(Employee employee, Instant start, Duration duration, String location) {
		super(start, duration, "Shift", location);
		this.employee = employee;
	}

	public Shift() {

	}
}
