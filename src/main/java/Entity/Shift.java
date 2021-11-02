package Entity;

import Entity.Employee;
import Entity.Event;

import java.time.Duration;
import java.time.Instant;

public class Shift extends Event {
	private Employee employee;

	public Shift(Employee employee, Instant start, Duration duration, String location) {
		super(start, duration, "Shift", location);
		this.employee = employee;
	}
}
