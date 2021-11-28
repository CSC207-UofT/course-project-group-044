package com.hr.service;

import com.hr.entity.Event;

import java.util.List;

public interface EventService {
    boolean deleteEvents(String date);

    List<Event> getEventsInSameDate(String date);
}
