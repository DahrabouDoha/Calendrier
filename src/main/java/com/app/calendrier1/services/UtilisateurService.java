package com.app.calendrier1.services;


import org.json.JSONObject;

import java.util.List;

public interface UtilisateurService {

    public abstract void add(JSONObject jsonObject);

    public abstract  int getIdByEmail(String email) ;
    public abstract List<String> getEmailsByIdEntreprise(int idEntreprise, int idUser);
    public abstract int getIdEntrepriseByEmail(String email);
}