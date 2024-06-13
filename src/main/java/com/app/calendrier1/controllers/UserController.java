package com.app.calendrier1.controllers;

import com.app.calendrier1.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/utilisateurs")
public class UserController {
    @Autowired
    private UtilisateurService userservice;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/id/email/{email}")
    public int getIdByEmail(@PathVariable String email) {
        return userservice.getIdByEmail(email);
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/emails/entreprise/{idEntreprise}")
    public List<String> getEmailsByIdEntreprise(@PathVariable int idEntreprise) {
        return userservice.getEmailsByIdEntreprise(idEntreprise);
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/id_entreprise/email/{email}")
    public int getIdEntrepriseByEmail(@PathVariable String email) {
        return userservice.getIdEntrepriseByEmail(email);
    }
}
