package it.uniroma3.siw.recstudio.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.recstudio.model.Utente;
import it.uniroma3.siw.recstudio.repository.UtenteRepository;

@Service
public class UtenteService {
	
	@Autowired
	private UtenteRepository utenteRepository;
	
	@Transactional
	public Utente inserisci(Utente utente) {
		return utenteRepository.save(utente);
	}
	
	@Transactional
	public void rimuovi(Utente utente) {
		utenteRepository.delete(utente);
	}
	
	@Transactional
	public List<Utente> tutti() {
		return (List<Utente>) utenteRepository.findAll();
	}
	
	@Transactional
	public Utente utentePerId(Long id) {
		Optional<Utente> utenteOptional = utenteRepository.findById(id);
		return utenteOptional.orElse(null);
	}
	
	@Transactional
	public List<Utente> utentiPerNome(String nome) {
		return (List<Utente>) utenteRepository.findByNome(nome);
	}
	
	@Transactional
	public List<Utente> utentiPerCognome(String cognome){
		return (List<Utente>) utenteRepository.findByCognome(cognome);
	}
	
	@Transactional
	public Utente utentePerNomeCognome(String nome, String cognome) {
		return utenteRepository.findByNomeAndCognome(nome, cognome);
	}
}
