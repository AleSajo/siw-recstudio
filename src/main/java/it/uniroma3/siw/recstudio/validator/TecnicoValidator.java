package it.uniroma3.siw.recstudio.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.uniroma3.siw.recstudio.model.Tecnico;
import it.uniroma3.siw.recstudio.service.TecnicoService;

@Component
public class TecnicoValidator implements Validator {
	
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private TecnicoService tecnicoService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Tecnico.class.equals(clazz);
	}

	@Override
	public void validate(Object o, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cognome", "required");

		if (!errors.hasErrors()) {
			logger.debug("confermato: valori non nulli");
			if (this.tecnicoService.exists((Tecnico)o)) {
				logger.debug("e' un duplicato");
				errors.reject("duplicate");
			}
		}
	}
}
