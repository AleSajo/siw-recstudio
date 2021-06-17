package it.uniroma3.siw.recstudio.repository;

import java.time.*;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.recstudio.model.Prenotazione;

public interface PrenotazioneRepository extends CrudRepository<Prenotazione, Long> {
	
	public Prenotazione findByOrario(LocalDateTime orario);
	
	public List<Prenotazione> findAllByOrderByOrarioAsc();
	
	@Query("SELECT p FROM Prenotazione p WHERE p.orario > :oggi ORDER BY p.orario ASC")
	public List<Prenotazione> findAllFromTodayOrderByOrarioAsc(@Param("oggi") LocalDateTime oggi);
	
	@Query("SELECT p FROM Prenotazione p WHERE p.orario < :oggi ORDER BY p.orario DESC")
	public List<Prenotazione> findAllPastOrderByOrarioDesc(@Param("oggi") LocalDateTime oggi);
}
