package it.uniroma3.siw.recstudio.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.recstudio.model.Progetto;
import it.uniroma3.siw.recstudio.model.Utente;

public interface ProgettoRepository extends CrudRepository<Progetto, Long> {
	
	public Progetto findByNome(String nome);
	
	public List<Progetto> findByTipologia(String tipologia);
	
	public List<Progetto> findByUtente(Utente utente);
	
	public List<Progetto> findAllByFinito(boolean b); 
	
}
