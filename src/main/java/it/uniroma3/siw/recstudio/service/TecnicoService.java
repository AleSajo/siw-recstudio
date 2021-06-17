package it.uniroma3.siw.recstudio.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.recstudio.model.Tecnico;
import it.uniroma3.siw.recstudio.repository.TecnicoRepository;

@Service
public class TecnicoService {
	
	@Autowired
	private TecnicoRepository tecnicoRepository;
	
	@Transactional
	public Tecnico inserisci(Tecnico tecnico) {
		return tecnicoRepository.save(tecnico);
	}
	
	@Transactional
	public void rimuovi(Tecnico tecnico) {
		tecnicoRepository.delete(tecnico);
	}
	
	@Transactional
	public Tecnico tecnicoPerId(Long id) {
		Optional<Tecnico> tecnicoOptional = tecnicoRepository.findById(id);
		return tecnicoOptional.orElse(null);
	}
	
	@Transactional
	public List<Tecnico> tutti() {
		return (List<Tecnico>) tecnicoRepository.findAll();
	}
	
	@Transactional
	public List<Tecnico> tecniciPerNome(String nome) {
		return (List<Tecnico>) tecnicoRepository.findByNome(nome);
	}
	
	@Transactional
	public List<Tecnico> tecniciPerCognome(String cognome) {
		return (List<Tecnico>) tecnicoRepository.findByCognome(cognome);
	}
	
	@Transactional
	public Tecnico tecnicoPerNomeCognome(String nome, String cognome) {
		return tecnicoRepository.findByNomeAndCognome(nome, cognome);
	}
	
	@Transactional
	public boolean exists(Tecnico tecnico) {
		if(this.tecnicoPerNomeCognome(tecnico.getNome(), tecnico.getCognome()) != null) {
			return true;
		} else {
			return false;
		}
	}
}
