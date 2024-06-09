package com.app.calendrier1.services.implementation;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import com.app.calendrier1.dtos.EventDTO;
import com.app.calendrier1.entities.Event;
import com.app.calendrier1.entities.Utilisateur;
import com.app.calendrier1.repositories.EventRepository;
import com.app.calendrier1.repositories.UtilisateurRepository;
import com.app.calendrier1.services.EventService;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import java.util.*;

@Service
public class EventServiceImplementation implements EventService {
    private UtilisateurRepository userRepository;
    private EventRepository eventRepository;
    private final CloseableHttpClient httpClient;

    @Value("${notification.url}")
    private String notificationUrl;

    @Autowired
    public EventServiceImplementation(CloseableHttpClient httpClient,
                                      UtilisateurRepository userRepository,
                                      EventRepository eventRepository) {
        this.httpClient = httpClient;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }
   @Override
    public void AjouterEvenement(EventDTO eventDTO, int organizerId) {
        // Créer un nouvel événement à partir du DTO
        Event event = new Event();
        event.setTitle(eventDTO.getTitle());
        event.setDate(eventDTO.getDate());
        event.setTime(eventDTO.getTime());
        event.setEventType(eventDTO.getEventType());
        event.setReunionType(eventDTO.getMeetingType());
        event.setRemoteUrl(eventDTO.getRemoteUrl());
        Utilisateur user = userRepository.getOne(organizerId);
        event.setOrganisateur(user);
        List<Utilisateur> par = new ArrayList<>();
        if(event.getEventType().equals("Reunion")) {
            List<Utilisateur> users = new ArrayList<>();
            for (String email : eventDTO.getParticipantEmails()) {
                Utilisateur participant = userRepository.findByEmail(email);
                users.add(participant);
                par.add(participant);
            }
            event.setParticipants(users);
        } else {
            // Récupérer tous les utilisateurs de la base de données
            par = userRepository.findAll();

            // Exclure l'organisateur de la liste des participants
            par.removeIf(u -> u.getId() == organizerId);

            // Assigner tous les utilisateurs restants comme participants à l'événement
            event.setParticipants(par);
        }



        // Convertir en chaîne JSON

        // Enregistrer l'événement dans la base de données

       JSONObject notificationData = new JSONObject();

       // Envoyez la notification en utilisant la méthode sendNotification

       eventRepository.save(event);
        // Envoyer les notifications
        for(Utilisateur user1:par){
            notificationData.put("invite",user1.getId());
            notificationData.put("title", event.getTitle());
            notificationData.put("date", event.getDate());
            notificationData.put("event", event.getId());
            notificationData.put("organisateur_email", event.getOrganisateur().getEmail());
            notificationData.put("message", event.getOrganisateur().getEmail()+"vous a ajoute a "+event.getTitle());

            System.out.print(notificationData);

            sendNotification(notificationData);
        }

    }

    @Override
    public List<String> getEventTitlesByOrganizerIdAndDate(int organizerId, String date) {
        Utilisateur organizer = userRepository.getOne(organizerId);
        List<Event> events = eventRepository.findByOrganisateurAndDate(organizer, date);

        List<String> eventTitles = new ArrayList<>();
        for (Event event : events) {
            if (event.getDate().equals(date)) { // Vérifier si la date de l'événement correspond à celle spécifiée
                eventTitles.add(event.getTitle());
            }
        }

        return eventTitles;
    }


    private void sendNotification(JSONObject notificationData) {
        // URL du microservice de gestion des notifications
        String url = "http://localhost:8082/notif/notifications/ajouter";

        // Créer un client HttpClient
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // Créer une requête POST
            HttpPost httpPost = new HttpPost(url);

            // Définir le type de contenu de la requête
            httpPost.setHeader("Content-Type", "application/json");

            // Convertir l'objet JSON en chaîne et créer une entité de requête
            StringEntity requestEntity = new StringEntity(notificationData.toString(), ContentType.APPLICATION_JSON);
            httpPost.setEntity(requestEntity);

            // Envoyer la requête et obtenir la réponse
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                // Gérer la réponse si nécessaire
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    System.out.println("Les données de notification ont été envoyées avec succès.");
                } else {
                    System.out.println("Erreur lors de l'envoi des données de notification. Code d'erreur : " + statusCode);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void supprimerNotification(int event) {
        // URL du microservice de gestion des notifications
        String url = "http://localhost:8082/notif/deleteByEvent/" + event;

        // Créer un client HttpClient
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // Créer une requête DELETE
            HttpDelete httpDelete = new HttpDelete(url);

            // Envoyer la requête et obtenir la réponse
            try (CloseableHttpResponse response = httpClient.execute(httpDelete)) {
                // Gérer la réponse si nécessaire
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    System.out.println("La notification a été supprimée avec succès.");
                } else {
                    System.out.println("Erreur lors de la suppression de la notification. Code d'erreur : " + statusCode);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }











    @Override

    public void deleteEventById(int eventId) {
        // Supprimer les entrées dans la table de liaison associées à cet événement

        supprimerNotification(eventId);
        eventRepository.deleteById(eventId);
    }
    @Override
    public List<Map<String, Object>> getEventById(int id) {
        Event event = eventRepository.getOne(id);

        List<Map<String, Object>> eventList = new ArrayList<>();

        Map<String, Object> eventData = new HashMap<>();
        eventData.put("id", event.getId());
        eventData.put("title", event.getTitle());
        eventData.put("date", event.getDate());
        eventData.put("time", event.getTime());
        eventData.put("TR", event.getReunionType());
        eventData.put("organisateur", event.getOrganisateur().getEmail());
        eventData.put("Url", event.getRemoteUrl());
        eventData.put("eventType", event.getEventType());
        eventData.put("participants", event.getParticipants().stream().map(Utilisateur::getEmail).collect(Collectors.toList()));

        eventList.add(eventData);

        return eventList;
    }

    @Override
   public List<Map<String, Object>> getEventsByOrganizerId(int organizerId) {
       Utilisateur organizer = userRepository.getOne(organizerId);
       List<Event> events = eventRepository.findByOrganisateur(organizer);

       List<Map<String, Object>> eventList = new ArrayList<>();
       for (Event event : events) {
           Map<String, Object> eventData = new HashMap<>();
           eventData.put("id", event.getId());
           eventData.put("title", event.getTitle());
           eventData.put("date", event.getDate());
           eventData.put("time", event.getTime());
           eventData.put("eventType", event.getEventType());
           eventData.put("TR", event.getReunionType());
           eventData.put("organisateur", event.getOrganisateur().getEmail());
           eventData.put("Url", event.getRemoteUrl());
           eventData.put("participants", event.getParticipants().stream().map(Utilisateur::getEmail).collect(Collectors.toList()));


           eventList.add(eventData);
       }

       return eventList;
   }
    @Override
    public List<Map<String, Object>> getEventsByMemberId(int memberId) {
        Utilisateur membre = userRepository.getOne(memberId); // Supposons que vous avez un référentiel userRepository
        List<Event> events = eventRepository.findByParticipants(membre); // Supposons que vous avez un référentiel eventRepository

        List<Map<String, Object>> eventList = new ArrayList<>();
        for (Event event : events) {
            Map<String, Object> eventData = new HashMap<>();
            eventData.put("id", event.getId());
            eventData.put("title", event.getTitle());
            eventData.put("date", event.getDate());
            eventData.put("time", event.getTime());
            eventData.put("eventType", event.getEventType());
            eventData.put("TR", event.getReunionType());
            eventData.put("organisateur", event.getOrganisateur().getEmail());
            eventData.put("Url", event.getRemoteUrl());
            eventData.put("participants", event.getParticipants().stream().map(Utilisateur::getEmail).collect(Collectors.toList()));

            eventList.add(eventData);
        }

        return eventList;
    }
}


