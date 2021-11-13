package com.hr.repository;

import com.hr.entity.Calendar;
import com.hr.entity.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.*;

@Repository
public interface CalendarRepository extends CrudRepository<Calendar, Instant> {
}