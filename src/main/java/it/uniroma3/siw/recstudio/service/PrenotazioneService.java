package it.uniroma3.siw.recstudio.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.recstudio.model.Prenotazione;
import it.uniroma3.siw.recstudio.repository.PrenotazioneRepository;

@Service
public class PrenotazioneService {
	
	@Autowired
	private PrenotazioneRepository prenotazioneRepository;
	
	@Transactional
	public Prenotazione inserisci(Prenotazione prenotazione) {
		return prenotazioneRepository.save(prenotazione);
	}
	
	@Transactional
	public void rimuovi(Prenotazione prenotazione) {
		prenotazioneRepository.delete(prenotazione);
	}
	
	@Transactional
	public List<Prenotazione> tutte() {
		return (List<Prenotazione>) prenotazioneRepository.findAll();
	}
	
	@Transactional
	public List<Prenotazione> tuttePerData() {
		return (List<Prenotazione>) prenotazioneRepository.findAllByOrderByOrarioAsc();
	}
	
	@Transactional
	public List<Prenotazione> tuttePerDataDaOggi() {
		return (List<Prenotazione>) prenotazioneRepository.findAllFromTodayOrderByOrarioAsc(LocalDateTime.now());
	}
	
	@Transactional
	public List<Prenotazione> tuttePerDataPassate() {
		return (List<Prenotazione>) prenotazioneRepository.findAllPastOrderByOrarioDesc(LocalDateTime.now());
	}
	
	@Transactional
	public Prenotazione prenotazionePerId(Long id) {
		Optional<Prenotazione> prenotazioneOptional = prenotazioneRepository.findById(id);
		return prenotazioneOptional.orElse(null);
	}
	
	@Transactional
	public Prenotazione prenotazionePerOrario(LocalDateTime orario) {
		return prenotazioneRepository.findByOrario(orario);
	}
}
