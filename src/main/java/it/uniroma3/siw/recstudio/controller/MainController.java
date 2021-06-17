package it.uniroma3.siw.recstudio.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.recstudio.model.Utente;
import it.uniroma3.siw.recstudio.service.CredentialsService;
import it.uniroma3.siw.recstudio.service.PrenotazioneService;
import it.uniroma3.siw.recstudio.service.ProgettoService;
import it.uniroma3.siw.recstudio.service.TecnicoService;
import it.uniroma3.siw.recstudio.service.UtenteService;

@Controller
public class MainController {
	
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private PrenotazioneService prenotazioneService;
    
    @Autowired
    private TecnicoService tecnicoService;
    
    @Autowired
    private ProgettoService progettoService;
    
    @Autowired 
    private CredentialsService credentialsService;
	
	@RequestMapping(value = {"/","/home"}, method = RequestMethod.GET)
	public String showHome(Model model) {
		logger.debug("logger: mainController.home");
		return "home";
	}
	
	@RequestMapping(value="/tecnici",method = RequestMethod.GET)
	public String showTecnici(Model model) {
		logger.debug("logger: MainController.showTecnici");
		return "tecnici";
	}
	
	@RequestMapping(value="/info",method = RequestMethod.GET)
	public String showInfo(Model model) {
		return "info";
	}
	
	@RequestMapping(value="/progetti",method = RequestMethod.GET)
	public String showProgetti(Model model) {
		return "progetti";
	}
	
	@RequestMapping(value="/areaUtente",method=RequestMethod.GET)
	public String showAreaUtente(Model model) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Utente utente = credentialsService.getCredentials(userDetails.getUsername()).getUtente();
		model.addAttribute("utente", utente);
		model.addAttribute("prenotazioni", utente.getPrenotazioni());
		model.addAttribute("progetti", utente.getProgetti());
		return "areaUtente";
	}
	
	@RequestMapping(value="/areaAdmin",method=RequestMethod.GET)
	public String showAreaAdmin(Model model) {
		logger.debug("logger: MainController.showAreaAdmin");
		model.addAttribute("prenotazioni", prenotazioneService.tuttePerDataDaOggi());
		model.addAttribute("tecnici", tecnicoService.tutti());
		model.addAttribute("progetti", progettoService.tuttiInCorso());
		model.addAttribute("check","check");
		return "admin/areaAdmin";
	}
	
	/*
	@RequestMapping(value="/navigation",method = RequestMethod.POST)
	public String showLogin(Model model) {
		logger.debug("logger: MainController.showLogin");
		return "login";
	}
	*/
}