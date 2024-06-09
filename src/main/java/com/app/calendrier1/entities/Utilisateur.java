package com.app.calendrier1.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "users")
public class Utilisateur {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;
	    @Column(name = "id_entreprise")

	    private int id_entreprise;
	    private String email;

	    @OneToMany(mappedBy = "organisateur")
		private List<Event> events;
	    @ManyToMany(mappedBy = "participants", fetch = FetchType.EAGER)
	    private List<Event> evs=new ArrayList<>();


}
