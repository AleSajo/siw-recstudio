package it.uniroma3.siw.recstudio.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.recstudio.model.Utente;

public interface UtenteRepository extends CrudRepository<Utente, Long> {
	
	public List<Utente> findByNome(String nome);
	
	public List<Utente> findByCognome(String cognome);
	
	public Utente findByNomeAndCognome(String nome, String cognome);
}
