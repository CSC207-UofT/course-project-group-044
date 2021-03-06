package com.hr.service;

import com.hr.entity.Event;

import java.time.Instant;
import java.util.List;

public interface EventService {
    boolean deleteEventsByDate(String date);

    List<Event> getEventsInSameDate(String date);

    void deleteEvent(Event event);

    void deleteEventByInstant(Instant start);

    Iterable<Event> findAllEvents();
}
