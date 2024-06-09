package com.app.calendrier1.Entities;

import jakarta.persistence.*;
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
@Table(name = "users")
public class Utilisateur {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int id;
	    private String username;
	    private String password;
	    private String email;
	    private String name;
	    @OneToMany(mappedBy = "organisateur", cascade = CascadeType.ALL, orphanRemoval = true)
		private List<Event> events;
	@ManyToMany(cascade = { CascadeType.ALL },mappedBy = "participants", fetch = FetchType.EAGER)
	private List<Event> evs=new ArrayList<>();
}
