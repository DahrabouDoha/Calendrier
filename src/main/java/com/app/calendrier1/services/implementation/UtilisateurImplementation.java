package com.app.calendrier1.services.implementation;

import com.app.calendrier1.entities.Utilisateur;
import com.app.calendrier1.repositories.UtilisateurRepository;
import com.app.calendrier1.services.UtilisateurService;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurImplementation implements UtilisateurService {
   private UtilisateurRepository userrepository;


   public UtilisateurImplementation(UtilisateurRepository userRepository){
       this.userrepository=userRepository;}

    public void add(JSONObject jsonObject){

        int id = jsonObject.getInt("id");
        int idEntreprise = jsonObject.getInt("id_entreprise");
        String email = jsonObject.getString("email");

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(id);
        utilisateur.setId_entreprise(idEntreprise);
        utilisateur.setEmail(email);

        userrepository.save(utilisateur);
    }
}
