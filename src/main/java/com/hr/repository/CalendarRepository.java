package com.hr.repository;

import com.hr.entity.Calendar;
import com.hr.entity.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.*;
import java.util.UUID;

@Repository
public interface CalendarRepository extends CrudRepository<Calendar, UUID> {
}