package it.uniroma3.siw.recstudio.service;

import java.util.*;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.recstudio.model.Progetto;
import it.uniroma3.siw.recstudio.model.Utente;
import it.uniroma3.siw.recstudio.repository.ProgettoRepository;

@Service
public class ProgettoService {

	@Autowired
	private ProgettoRepository progettoRepository;

	@Transactional
	public Progetto inserisci(Progetto progetto) {
		return progettoRepository.save(progetto);
	}

	@Transactional
	public void rimuovi(Progetto progetto) {
		progettoRepository.delete(progetto);
	}

	@Transactional
	public List<Progetto> tutti() {
		return (List<Progetto>) progettoRepository.findAll();
	}
	
	@Transactional
	public List<Progetto> tuttiInCorso() {
		return (List<Progetto>) progettoRepository.findAllByFinito(false);
	}

	@Transactional
	public Progetto progettoPerId(Long id) {
		Optional<Progetto> progettoOptional = progettoRepository.findById(id);
		return progettoOptional.orElse(null);
	}
	
	@Transactional
	public Progetto progettoPerNome(String nome) {
		return progettoRepository.findByNome(nome);
	}
	
	@Transactional
	public List<Progetto> progettiPerTipologia(String tipologia) {
		return (List<Progetto>) progettoRepository.findByTipologia(tipologia);
	}
	
	@Transactional
	public boolean exists(Progetto progetto) {
		if(this.progettoPerNome(progetto.getNome()) != null) {
			return true;
		} else {
			return false;
		}
	}
}
