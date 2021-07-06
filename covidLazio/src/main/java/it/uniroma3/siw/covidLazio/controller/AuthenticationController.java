package it.uniroma3.siw.covidLazio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.covidLazio.model.Amministratore;
import it.uniroma3.siw.covidLazio.model.Credentials;
import it.uniroma3.siw.covidLazio.service.CredentialsService;
import it.uniroma3.siw.covidLazio.validator.CredentialsValidator;
import it.uniroma3.siw.covidLazio.validator.UtenteValidator;



@Controller
public class AuthenticationController {
	
	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private UtenteValidator utenteValidator;
	
	@Autowired
	private CredentialsValidator credentialsValidator;



	
	@RequestMapping(value = "/register", method = RequestMethod.GET) 
	public String showRegisterForm (Model model) {
		model.addAttribute("user", new Amministratore());
		model.addAttribute("credentials", new Credentials());
		return "registerForm";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET) 
	public String mostraLoginForm (Model model) {
		return "loginForm";
	}
	
	@RequestMapping(value = "/default", method = RequestMethod.GET)
    public String defaultDopoLogin(Model model) {
		UserDetails userDetails =(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("nomeAdmin",userDetails.getUsername());
		return "admin/home";
    }
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET) 
	public String logout(Model model) {
		return "index";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerUser(@ModelAttribute("user") Amministratore user,
                 BindingResult userBindingResult,
                 @ModelAttribute("credentials") Credentials credentials,
                 BindingResult credentialsBindingResult,
                 Model model) {

        // validate user and credentials fields
        this.utenteValidator.validate(user, userBindingResult);
        this.credentialsValidator.validate(credentials, credentialsBindingResult);

        // if neither of them had invalid contents, store the User and the Credentials into the DB
        if(!userBindingResult.hasErrors() && ! credentialsBindingResult.hasErrors()) {
            // set the user and store the credentials;
            // this also stores the User, thanks to Cascade.ALL policy
            credentials.setAmministratore(user);
            credentialsService.saveCredentials(credentials);
            return "registrationSuccessful";
        }
        return "registerForm";
    }
}
