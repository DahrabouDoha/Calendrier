package com.app.calendrier1.entities;



import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "events")
public class Event {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = true)
    private String title;

    private String date;

    private String time;


    private String eventType;
    @Column(nullable = true)
    private String reunionType;
    @Column(nullable = true)
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
