package com.app.calendrier1.services;


import com.app.calendrier1.dtos.EventDTO;
import com.app.calendrier1.entities.Event;

import java.util.List;
import java.util.Map;


public interface EventService {
    public abstract void AjouterEvenement(EventDTO eventDTO, int organizerId);

    public abstract void deleteEventById(int eventId);
    public abstract List<Map<String, Object>> getEventsByOrganizerId(int organizerId);

    public abstract List<Map<String, Object>> getEventsByMemberId(int organizerId);
    public abstract List<Map<String, Object>> getEventById(int id);

    List<String> getEventTitlesByOrganizerIdAndDate(int organizerId, String date);
}
