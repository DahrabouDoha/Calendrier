package com.app.calendrier1.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "events")
public class Event {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String date;
    private String time;


    private String eventType;
    private String reunionType;
    private String remoteUrl;

    @ManyToMany
    @JoinTable(
            name = "event_member",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<Utilisateur> participants=new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur organisateur;


}
