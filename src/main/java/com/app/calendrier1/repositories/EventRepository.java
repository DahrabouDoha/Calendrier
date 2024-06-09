package com.app.calendrier1.repositories;

import com.app.calendrier1.entities.Event;
import com.app.calendrier1.entities.Utilisateur;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event,Integer> {
    @Transactional
    void deleteById(int id);
    List<Event> findByOrganisateurAndDate(Utilisateur organizer, String date);

    List<Event> findByOrganisateur(Utilisateur organizer);

    List<Event> findByParticipants(Utilisateur participant);
}
