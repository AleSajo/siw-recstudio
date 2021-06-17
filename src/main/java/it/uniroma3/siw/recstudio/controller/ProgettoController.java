package it.uniroma3.siw.recstudio.controller;

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

import it.uniroma3.siw.recstudio.model.Progetto;
import it.uniroma3.siw.recstudio.model.Utente;
import it.uniroma3.siw.recstudio.service.CredentialsService;
import it.uniroma3.siw.recstudio.service.ProgettoService;
import it.uniroma3.siw.recstudio.validator.ProgettoValidator;

@Controller
public class ProgettoController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ProgettoService progettoService;
	
	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private ProgettoValidator progettoValidator;
	
	@RequestMapping(value="/nuovoProgetto",method = RequestMethod.GET)
	public String mostraNuovoProgetto(Model model) {
		model.addAttribute("progetto", new Progetto());
		return "nuovoProgetto";
	}
	
	@RequestMapping(value="/creaProgetto",method = RequestMethod.POST)
	public String creaProgetto(Model model,
			@ModelAttribute("progetto") Progetto progetto,
			BindingResult br) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Utente utente = credentialsService.getCredentials(userDetails.getUsername()).getUtente();
		progetto.setUtente(utente);
		logger.debug("logger: "+progetto.toString());
		this.progettoValidator.validate(progetto, br);
		if(!br.hasErrors()) {
			progettoService.inserisci(progetto);
			model.addAttribute("utente", utente);
			model.addAttribute("prenotazioni", utente.getPrenotazioni());
			model.addAttribute("progetti", utente.getProgetti());
			return "areaUtente";
		}
		return "nuovoProgetto";
	}
}
