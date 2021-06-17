package it.uniroma3.siw.recstudio.model;

import java.time.LocalDate;
import java.util.*;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name="utenti")
@Data
@ToString(onlyExplicitlyIncluded = true)
public class Utente {

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
	private String nomeArte;
	
	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataDiNascita;
	
	@Column 
	private String indirizzo;
	
	@Column
	private String email;
	
	@OneToMany(mappedBy="utente",
			fetch=FetchType.EAGER,
			cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	private List<Progetto> progetti;
	
	@OneToMany(mappedBy="utente",
			fetch=FetchType.EAGER)
	private List<Prenotazione> prenotazioni;
	
	public Utente() {
		this.progetti = new ArrayList<Progetto>();
		this.prenotazioni = new ArrayList<Prenotazione>();
	}
}