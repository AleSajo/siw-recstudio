package it.uniroma3.siw.recstudio.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.recstudio.model.Tecnico;

public interface TecnicoRepository extends CrudRepository<Tecnico, Long> {

	public List<Tecnico> findByNome(String nome);
	
	public List<Tecnico> findByCognome(String cognome);
	
	public Tecnico findByNomeAndCognome(String nome, String cognome);
}
