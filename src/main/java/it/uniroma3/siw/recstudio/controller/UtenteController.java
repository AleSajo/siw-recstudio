package it.uniroma3.siw.recstudio.controller;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.recstudio.model.Utente;
import it.uniroma3.siw.recstudio.service.CredentialsService;
import it.uniroma3.siw.recstudio.service.UtenteService;

@Controller
public class UtenteController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UtenteService utenteService;
	
	@Autowired
	private CredentialsService credentialsService;
	
	@RequestMapping(value="/modificaInfoForm",method = RequestMethod.GET)
	public String mostraModificaInfoForm(Model model) {
		//prendo l'utente attualmente loggato
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Utente utente = credentialsService.getCredentials(userDetails.getUsername()).getUtente();
		model.addAttribute("utente", utente);
		return "modificaInfoForm";
	}
	
	@RequestMapping(value="/confermaModificaInfo",method = RequestMethod.POST)
	public String confermaModificaInfo(Model model,
			@ModelAttribute("utente") Utente utenteModificato) {
		//prendo l'utente loggato
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Utente utente = credentialsService.getCredentials(userDetails.getUsername()).getUtente();
		//ci metto dentro i nuovi campi
		utente.setNomeArte(utenteModificato.getNomeArte());
		utente.setDataDiNascita(utenteModificato.getDataDiNascita());
		utente.setIndirizzo(utenteModificato.getIndirizzo());
		utente.setEmail(utenteModificato.getEmail());
		//salvo l'utente loggato
		utenteService.inserisci(utente);
		logger.debug("logger: "+utente.toString());
		//restituisco al model l'utente loggato
		model.addAttribute("utente", utente);
		model.addAttribute("prenotazioni", utente.getPrenotazioni());
		return "areaUtente";
	}
}
