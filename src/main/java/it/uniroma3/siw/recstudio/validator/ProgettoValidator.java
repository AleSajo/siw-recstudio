package it.uniroma3.siw.recstudio.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import it.uniroma3.siw.recstudio.model.Progetto;
import it.uniroma3.siw.recstudio.service.ProgettoService;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ProgettoValidator implements Validator{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ProgettoService progettoService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Progetto.class.equals(clazz);
	}

	@Override
	public void validate(Object o, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome", "required");

		if (!errors.hasErrors()) {
			logger.debug("confermato: valori non nulli");
			if (this.progettoService.exists((Progetto)o)) {
				logger.debug("e' un duplicato");
				errors.reject("duplicate");
			}
		}
	}
}
