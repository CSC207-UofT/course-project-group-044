package com.hr.service.impl;

import com.hr.entity.Event;
import com.hr.repository.EventRepository;
import com.hr.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    @Autowired
    private EventRepository eventRepository;

    @Override
    public boolean deleteEvents(String date) {
        LocalDate localDate = localDateconverter(date);

        List<Event> allEvents = new ArrayList<>();

        eventRepository.findAll().forEach(allEvents::add);

        for (Event event : allEvents) {
            if (event.getDate().isEqual(localDate)) {
                eventRepository.delete(event);
            }
        }

        return true;
    }

    @Override
    public List<Event> getEventsInSameDate(String date) {
        LocalDate localDate = localDateconverter(date);

        List<Event> allEvents = new ArrayList<>();
        List<Event> results = new ArrayList<>();

        eventRepository.findAll().forEach(allEvents::add);

        for (Event event : allEvents) {
            if (event.getDate().isEqual(localDate)) {
                results.add(event);
            }
        }

        return results;
    }

    private LocalDate localDateconverter(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);

        return localDate;
    }
}
