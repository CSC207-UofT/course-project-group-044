package com.hr.repository;

import com.hr.entity.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface EventRepository extends CrudRepository<Event, Instant> {
}