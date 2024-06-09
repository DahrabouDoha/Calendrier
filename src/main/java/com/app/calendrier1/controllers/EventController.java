package com.app.calendrier1.controllers;

import com.app.calendrier1.dtos.EventDTO;
import com.app.calendrier1.services.UtilisateurService;
import com.fasterxml.jackson.databind.JsonNode;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.app.calendrier1.services.EventService;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/events")
public class EventController {
    private EventService eventService;
    private UtilisateurService userservice;

    @Autowired
    public EventController(EventService eventService,UtilisateurService userservice) {
        this.eventService = eventService;
        this.userservice=userservice;
    }
    @PostMapping("/utilisateur/add")
    public void addUtilisateur(@RequestBody  JsonNode jsonNode) {
        // Convertir JsonNode en JSONObject
        JSONObject jsonObject = new JSONObject(jsonNode.toString());

        // Appeler notifService.Ajouter avec le JSONObject
        userservice.add(jsonObject);}
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/add/{organizerId}")
    public ResponseEntity<String> addEvent(@RequestBody EventDTO eventDTO, @PathVariable int organizerId) {
        try {
            eventService.AjouterEvenement(eventDTO,organizerId);
            return ResponseEntity.status(HttpStatus.CREATED).body("Event added successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add event.");
        }
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{id}")
    public ResponseEntity<?> getEventById(@PathVariable int id) {
        try {
            List<Map<String, Object>> eventList = eventService.getEventById(id);
            return ResponseEntity.ok(eventList);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/events/organizer/{organizerId}")
    public List<Map<String, Object>> getEventsByOrganizerId(@PathVariable int organizerId) {
        return eventService.getEventsByOrganizerId(organizerId);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/events/{eventId}")
    public ResponseEntity<String> deleteEvent(@PathVariable int eventId) {
        try {
            eventService.deleteEventById(eventId);
            return ResponseEntity.ok("Event deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete event.");
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/events/organizer/{organizerId}/{date}")
    public List<String> getEventTitlesByOrganizerAndDate(@PathVariable int organizerId, @PathVariable String date) {
        return eventService.getEventTitlesByOrganizerIdAndDate(organizerId, date);
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/events/member/{memberId}")
    public List<Map<String, Object>> getEventsByMemberId(@PathVariable int memberId) {
        return eventService.getEventsByMemberId(memberId);
    }

}
