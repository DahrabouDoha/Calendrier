package com.app.calendrier1.services.implementation;

import com.app.calendrier1.entities.Utilisateur;
import com.app.calendrier1.repositories.UtilisateurRepository;
import com.app.calendrier1.services.UtilisateurService;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        utilisateur.setIdEntreprise(idEntreprise);
        utilisateur.setEmail(email);

        userrepository.save(utilisateur);
    }


    public int getIdByEmail(String email) {
        Utilisateur utilisateur = userrepository.findByEmail(email);
        return utilisateur != null ? utilisateur.getId() : -1;
    }
    public List<String> getEmailsByIdEntreprise(int idEntreprise) {
        List<Utilisateur> utilisateurs = userrepository.findByIdEntreprise(idEntreprise);
        return utilisateurs.stream().map(Utilisateur::getEmail).collect(Collectors.toList());
    }
    public  int getIdEntrepriseByEmail(String email){
        Utilisateur utilisateur = userrepository.findByEmail(email);
        return utilisateur != null ? utilisateur.getIdEntreprise() : -1;
    }

}
