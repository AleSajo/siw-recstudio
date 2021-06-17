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

import it.uniroma3.siw.recstudio.validator.CredentialsValidator;
import it.uniroma3.siw.recstudio.validator.UtenteValidator;
import it.uniroma3.siw.recstudio.model.Credentials;
import it.uniroma3.siw.recstudio.model.Utente;
import it.uniroma3.siw.recstudio.service.CredentialsService;

@Controller
public class AuthController {
	
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private UtenteValidator utenteValidator;
	
	@Autowired
	private CredentialsValidator credentialsValidator;
	
	@RequestMapping(value = "/register", method = RequestMethod.GET) 
	public String showRegisterForm (Model model) {
		model.addAttribute("utente", new Utente());
		model.addAttribute("credentials", new Credentials());
		return "register";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET) 
	public String showLoginForm (Model model) {
		logger.info("logger showLoginForm");
		return "login";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET) 
	public String logout(Model model) {
		return "home";
	}
	
    @RequestMapping(value = "/default", method = RequestMethod.GET)
    public String defaultAfterLogin(Model model) {
        logger.debug("defaultAfterLogin");
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
    	if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
            return "home";
        }
        return "home";
    }
	
    @RequestMapping(value = { "/register" }, method = RequestMethod.POST)
    public String registerUser(@ModelAttribute("utente") Utente utente,
                 BindingResult utenteBindingResult,
                 @ModelAttribute("credentials") Credentials credentials,
                 BindingResult credentialsBindingResult,
                 Model model) {

        // validate user and credentials fields
        this.utenteValidator.validate(utente, utenteBindingResult);
        this.credentialsValidator.validate(credentials, credentialsBindingResult);

        // if neither of them had invalid contents, store the User and the Credentials into the DB
        if(!utenteBindingResult.hasErrors() && ! credentialsBindingResult.hasErrors()) {
            // set the user and store the credentials;
            // this also stores the User, thanks to Cascade.ALL policy
            credentials.setUtente(utente);
            credentialsService.saveCredentials(credentials);
            model.addAttribute("messaggioConferma", "Registrazione effettuata con successo. Adesso puoi fare il login");
            return "login";
        }
        return "register";
    }
}
