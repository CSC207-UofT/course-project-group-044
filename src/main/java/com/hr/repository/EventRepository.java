package com.hr.repository;

import com.hr.entity.Event;
import com.hr.entity.Shift;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.*;

@Repository
public interface EventRepository extends CrudRepository<Event, Instant> {
}