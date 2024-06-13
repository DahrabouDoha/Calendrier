package com.app.calendrier1.repositories;

import com.app.calendrier1.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur,Integer> {
    Utilisateur findByEmail(String email);
    List<Utilisateur> findByIdEntreprise(int idEntreprise);

}
