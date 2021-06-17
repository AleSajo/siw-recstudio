package it.uniroma3.siw.recstudio.model;

import java.util.List;

import javax.persistence.*;

import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString(onlyExplicitlyIncluded = true)
public class Tecnico {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@ToString.Include
	private Long id;
	
	@Column
	@ToString.Include
	private String nome;
	
	@Column
	@ToString.Include
	private String cognome;
	
	@Column
	private String email;
	
	@Column
	private String telefono;
	
	@OneToMany(mappedBy="tecnico",
			fetch=FetchType.EAGER,
			cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	private List<Progetto> progetti;
	
	@OneToMany(mappedBy="tecnico",
			fetch=FetchType.EAGER,
			cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	private List<Prenotazione> prenotazioni;
}
