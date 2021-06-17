package it.uniroma3.siw.recstudio.model;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString(onlyExplicitlyIncluded = true)
public class Progetto {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@ToString.Include
	private Long id;
	
	@Column
	@ToString.Include
	private String nome;
	
	@Column
	private LocalDate dataInizio;
	
	@Column
	private boolean finito;
	
	@Column
	private String descrizione;
	
	@Column
	private String tipologia;	//un attributo che indica il tipo di lavoro
	
	@Column
	private String genereMusicale;
	
	@Column
	private int prezzoTotale;	//il costo complessivo del progetto finora
	
	@ManyToOne(fetch=FetchType.EAGER,cascade= {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH})
	private Utente utente;
	
	@ManyToOne(fetch=FetchType.EAGER,cascade= {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH})
	private Tecnico tecnico;
	
	@OneToMany(mappedBy="progetto",
			fetch=FetchType.EAGER,
			cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	private List<Prenotazione> prenotazioni;
	
	public Progetto() {
		this.finito = false;
		this.dataInizio = LocalDate.now();
		this.prezzoTotale = 0;
		this.prenotazioni = new ArrayList<Prenotazione>();
	}
}
