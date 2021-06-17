package it.uniroma3.siw.recstudio.model;

import java.time.*;

import javax.persistence.*;

import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString(onlyExplicitlyIncluded = true)
public class Prenotazione {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@ToString.Include
	private Long id;
	
	@Column
	@ToString.Include
	private LocalDateTime orario;
	
	@Column
	private int prezzo;
	
	@ManyToOne(fetch=FetchType.EAGER,
			cascade= {CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.MERGE})
	private Utente utente;
	
	@ManyToOne(fetch=FetchType.EAGER,
			cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	private Progetto progetto;
	
	@ManyToOne(fetch=FetchType.EAGER,
			cascade= {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	private Tecnico tecnico;
	
	public Prenotazione() {
		
	}
}
