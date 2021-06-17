package it.uniroma3.siw.recstudio.controller;

import java.time.LocalDateTime;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sun.org.apache.bcel.internal.generic.NEW;

import it.uniroma3.siw.recstudio.model.Prenotazione;
import it.uniroma3.siw.recstudio.model.Tecnico;
import it.uniroma3.siw.recstudio.model.Utente;
import it.uniroma3.siw.recstudio.service.CredentialsService;
import it.uniroma3.siw.recstudio.service.PrenotazioneService;
import it.uniroma3.siw.recstudio.service.ProgettoService;
import it.uniroma3.siw.recstudio.service.TecnicoService;
import it.uniroma3.siw.recstudio.service.UtenteService;

@Controller
public class PrenotazioneController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PrenotazioneService prenotazioneService;
	
	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private UtenteService utenteService;
	
	@Autowired
	private TecnicoService tecnicoService;
	
	@Autowired
	private ProgettoService progettoService;
	
	/* METODI PER UTENTE */
	
	@RequestMapping(value="/prenota",method=RequestMethod.GET)
	public String showPrenota(Model model) {
		model.addAttribute("prenotazioni", prenotazioneService.tuttePerDataDaOggi());
		return "prenota";
	}
	
	@RequestMapping(value="/prenota/{id}",method=RequestMethod.GET)
	public String prenotaSlot(@PathVariable("id") Long id ,Model model) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Utente utente = credentialsService.getCredentials(userDetails.getUsername()).getUtente();
		Prenotazione prenotazione = prenotazioneService.prenotazionePerId(id);
		prenotazione.setUtente(utente);
		utente.getPrenotazioni().add(prenotazione);
		prenotazioneService.inserisci(prenotazione);
		model.addAttribute("utente", utente);
		model.addAttribute("prenotazioni", utente.getPrenotazioni());
		return "areaUtente";
	}
	
	@RequestMapping(value="/annullaPrenotazione/{id}",method = RequestMethod.GET)
	public String annullaPrenotazione(@PathVariable("id") Long id,Model model) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Utente utente = credentialsService.getCredentials(userDetails.getUsername()).getUtente();
		Prenotazione prenotazione = prenotazioneService.prenotazionePerId(id);
		prenotazione.setUtente(null);
		utente.getPrenotazioni().remove(prenotazione);
		prenotazioneService.inserisci(prenotazione);
		model.addAttribute("utente", utente);
		model.addAttribute("prenotazioni", utente.getPrenotazioni());
		return "areaUtente";
	}
	
	/* METODI PER AMMINISTRATORE */
	
	@RequestMapping(value="/prenotazioniPassate",method = RequestMethod.GET)
	public String showPrenotazioniPassate(Model model) {
		logger.debug("logger: MainController.showPrenotazioniPassate");
		model.addAttribute("prenotazioni", prenotazioneService.tuttePerDataPassate());
		return "admin/areaAdmin";
	}
	
	@RequestMapping(value="/nuovaSlot",method = RequestMethod.GET)
	public String mostraNuovaSlot(Model model) {
		return "admin/nuovaSlot";
	}
	
	@RequestMapping(value="/creaSlot",method = RequestMethod.POST)
	public String creaSlot(
			@RequestParam String orario,
			Model model) {
		logger.error("logger: PrenotazioneController.creaSlot");
		logger.error(orario);
		Prenotazione nuovaPrenotazione = new Prenotazione();
		nuovaPrenotazione.setOrario(LocalDateTime.parse(orario));
		prenotazioneService.inserisci(nuovaPrenotazione);
		model.addAttribute("prenotazioni", prenotazioneService.tuttePerDataDaOggi());
		model.addAttribute("check", "check");
		return "/admin/areaAdmin";
	}
	
	@RequestMapping(value="/eliminaSlot/{id}",method = RequestMethod.GET)
	public String eliminaSlot(@PathVariable("id") Long id, Model model) {
		if(id!=null) {
			prenotazioneService.rimuovi(prenotazioneService.prenotazionePerId(id));
		}
		model.addAttribute("prenotazioni", prenotazioneService.tuttePerDataDaOggi());
		model.addAttribute("check", "check");
		return "/admin/areaAdmin";
	}
	
	@RequestMapping(value="/revocaEliminaSlot/{id}",method = RequestMethod.GET)
	public String revocaEliminaSlot(@PathVariable("id") Long id, Model model) {
		Prenotazione prenotazione = prenotazioneService.prenotazionePerId(id);
		Utente utente = prenotazione.getUtente();
		utente.getPrenotazioni().remove(prenotazione);
		prenotazione.setUtente(null);
		utenteService.inserisci(utente);
		prenotazioneService.rimuovi(prenotazione);
		model.addAttribute("prenotazioni", prenotazioneService.tuttePerDataDaOggi());
		model.addAttribute("check", "check");
		return "admin/areaAdmin";
	}
	
	@RequestMapping(value="/modificaTecnicoPrenotazione/{id}",method = RequestMethod.GET)
	public String mostraModificaTecnicoPrenotazione(@PathVariable("id") Long id,
			Model model) {
		model.addAttribute("prenotazione", prenotazioneService.prenotazionePerId(id));
		model.addAttribute("tecnici", tecnicoService.tutti());
		return "admin/modificaTecnicoPrenotazione";
	}
	
	@RequestMapping(value="/confermaModificaTecnicoPrenotazione/{id}",method = RequestMethod.POST)
	public String confermaModificaTecnicoPrenotazione(@PathVariable("id") Long id,
			@ModelAttribute("prenotazione") Prenotazione prenotazione,
			Model model) {
		//prendo il tecnico dal db a cui corrisponde l'id
		Tecnico tecnico = tecnicoService.tecnicoPerId(prenotazione.getTecnico().getId());
		Prenotazione prenotazioneCorrente = prenotazioneService.prenotazionePerId(id);
		prenotazioneCorrente.setTecnico(tecnico);
		prenotazioneService.inserisci(prenotazioneCorrente);
		
		model.addAttribute("prenotazioni", prenotazioneService.tuttePerDataDaOggi());
		model.addAttribute("tecnici", tecnicoService.tutti());
		model.addAttribute("progetti", progettoService.tuttiInCorso());
		model.addAttribute("check","check");
		return "admin/areaAdmin";
	}
}
