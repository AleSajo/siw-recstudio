package it.uniroma3.siw.recstudio.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.recstudio.model.Tecnico;
import it.uniroma3.siw.recstudio.service.PrenotazioneService;
import it.uniroma3.siw.recstudio.service.ProgettoService;
import it.uniroma3.siw.recstudio.service.TecnicoService;
import it.uniroma3.siw.recstudio.validator.TecnicoValidator;

@Controller
public class TecnicoController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private TecnicoService tecnicoService;
	
	@Autowired
	private TecnicoValidator tecnicoValidator;
	
	@Autowired
	private PrenotazioneService prenotazioneService;
	
	@Autowired
	private ProgettoService progettoService;
	
	@RequestMapping(value="/nuovoTecnico",method = RequestMethod.GET)
	public String mostraNuovoTecnico(Model model) {
		model.addAttribute("tecnico", new Tecnico());
		return "admin/nuovoTecnico";
	}
	
	@RequestMapping(value="/creaTecnico",method = RequestMethod.POST)
	public String creaTecnico(Model model,
			@ModelAttribute("tecnico") Tecnico tecnico,
			BindingResult br) {
		logger.debug("logger: "+tecnico.toString());
		this.tecnicoValidator.validate(tecnico, br);
		if(!br.hasErrors()) {
			tecnicoService.inserisci(tecnico);
			model.addAttribute("prenotazioni", prenotazioneService.tuttePerDataDaOggi());
			model.addAttribute("tecnici", tecnicoService.tutti());
			model.addAttribute("progetti", progettoService.tuttiInCorso());
			model.addAttribute("check","check");
			return "admin/areaAdmin";
		}
		//devo fare validazione e poi caricare su db
		//nella validazione rifiuto tecnici duplicati
		return "admin/nuovoTecnico";
	}
}
