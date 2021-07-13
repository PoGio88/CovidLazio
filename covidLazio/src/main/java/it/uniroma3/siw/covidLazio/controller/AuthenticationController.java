package it.uniroma3.siw.covidLazio.controller;

import it.uniroma3.siw.covidLazio.model.Utente;
import it.uniroma3.siw.covidLazio.model.Vaccino;
import it.uniroma3.siw.covidLazio.service.ComuniService;
import it.uniroma3.siw.covidLazio.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.covidLazio.model.Credentials;
import it.uniroma3.siw.covidLazio.service.CredentialsService;
import it.uniroma3.siw.covidLazio.controller.validator.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@Controller
public class AuthenticationController {

    @Autowired
    private CredentialsService credentialsService;

    @Autowired
    private UtenteValidator utenteValidator;

    @Autowired
    private CredentialsValidator credentialsValidator;

    @Autowired
    private ComuniService comuniService;

    @Autowired
    private UtenteService utenteService;


    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new Utente());
        model.addAttribute("credentials", new Credentials());
        model.addAttribute("localita",comuniService.tutteLeLocalita());
        return "registerForm";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String mostraLoginForm(Model model) {
        return "loginForm";
    }

    @RequestMapping(value = "/default", method = RequestMethod.GET)
    public String defaultDopoLogin(Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
        model.addAttribute("utente",credentials.getUtente());
        return "home.html";
    }

    @RequestMapping(value = "/default/oauth2",method = RequestMethod.GET)
    public String defaultDopoOauth(Model model) {
        Credentials credentials = null;
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal().getClass().equals(DefaultOidcUser.class)) {
            OAuth2AuthenticatedPrincipal oAuth2AuthenticatedPrincipal = (OAuth2AuthenticatedPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String email = oAuth2AuthenticatedPrincipal.getAttribute("email");
            Utente utente = utenteService.utentePerEmail(email);
            credentials = credentialsService.getCredentials(utente);
            List<GrantedAuthority> actualAuthorities = new ArrayList<>();
            actualAuthorities.add(new SimpleGrantedAuthority(credentials.getRole()));
            UserDetails details = User.withUsername(credentials.getUsername()).password(credentials.getPassword()).authorities(actualAuthorities).build();
            Authentication authentication = new UsernamePasswordAuthenticationToken(details, null, actualAuthorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        else {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            credentials = credentialsService.getCredentials(userDetails.getUsername());
        }
        model.addAttribute("utente",credentials.getUtente());
        model.addAttribute("userName",credentials.getUsername());
        return "home.html";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(Model model) {
        return "index";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerUser(@ModelAttribute("user") @Valid Utente utente,
                               BindingResult userBindingResult,
                               @ModelAttribute("credentials") @Valid Credentials credentials,
                               BindingResult credentialsBindingResult,
                               Model model) {

        // validate user and credentials fields
        this.utenteValidator.validate(utente, userBindingResult);
        this.credentialsValidator.validate(credentials, credentialsBindingResult);

        // if neither of them had invalid contents, store the User and the Credentials into the DB
        if (!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
            // set the user and store the credentials;
            // this also stores the User, thanks to Cascade.ALL policy
            credentials.setUtente(utente);
            credentialsService.saveCredentials(credentials);
            return "index.html";
        }
        model.addAttribute("localita",comuniService.tutteLeLocalita());
        return "registerForm.html";
    }
}
