package com.facundoprecentado.controller;

import com.facundoprecentado.domain.*;
import com.facundoprecentado.repository.AsociadoRepository;
import com.facundoprecentado.repository.SocioRepository;
import com.facundoprecentado.repository.UbicacionRepository;
import com.facundoprecentado.repository.UserRepository;
import com.facundoprecentado.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.SecurityContextProvider;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private MailService mailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UbicacionRepository ubicacionRepository;

    @Autowired
    private AsociadoRepository asociadoRepository;

    @Autowired
    private SocioRepository socioRepository;

    @RequestMapping("/")
    public String index() {
        return "home";
    }

    @RequestMapping("/home")
    public String home(Model model, Principal principal) {
        List<Ubicacion> departamentos = ubicacionRepository.findDepartamentos();
        model.addAttribute("departamentos", departamentos);

        if(principal != null) { // Esta logeado
            log.info("Usuario logeado: " + principal.getName());
        }

        return "home";
    }

    @RequestMapping("/plans")
    public String plans() {
        return "plans";
    }

    @RequestMapping("/login")
    public String login(Model model, Principal principal) {
        model.addAttribute("user", new User());

        if(principal != null) { // Esta logeado
            log.info("principal " + principal.getName());
            User user = userRepository.findByUsername(principal.getName());
            if(user.getType() == 0) { // Es Socio
                // TODO Tengo que buscar sus datos y ver si existe.
                Socio socio = socioRepository.findOne(user.getUsername());
                if(socio == null) { // No tiene nada guardado
                    socio = new Socio();
                    socio.setUsername(user.getUsername());
                }

                model.addAttribute("socio", socio);

                return "logged-socio";
            } else if(user.getType() == 1) { // Es Asociado
                return "logged-asociado";
            }
        } else {
            log.info("not logged");
        }

        return "login";
    }

    /*
     * Busco las Provincias por el ID Departamento
     */
    @RequestMapping(value = "/provincias/{idDepartamento}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    List<Ubicacion> getProvinciasByIdDepartamento(@PathVariable(value="idDepartamento") Integer idDepartamento) {
        log.info("getProvinciasByIdDepartamento " + idDepartamento);
        List<Ubicacion> provincias = ubicacionRepository.findProvinciasByDepartamento(idDepartamento);
        return provincias;
    }

    /*
     * Busco los Distritos por el ID Departamento y el ID Provincia
     */
    @RequestMapping(value = "/distritos/{idDepartamento}/{idProvincia}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    List<Ubicacion> getDistritosByIdProvincia(@PathVariable(value="idDepartamento") Integer idDepartamento, @PathVariable(value="idProvincia") Integer idProvincia) {
        log.info("getDistritosByIdProvincia " + idDepartamento + idProvincia);
        List<Ubicacion> distritos = ubicacionRepository.findDistritosByDepartamentoAndProvincia(idDepartamento, idProvincia);

        return distritos;
    }

    /*
     * Busco los Asoaciados que perteneces a un ID Distrito especifico
     */
    @RequestMapping(value = "/asociados/byDistrito/{idDistrito}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    List<Asociado> getAsociadosByIdDistrito(@PathVariable(value="idDistrito") Integer idDistrito) {
        log.info("getAsociadosByIdDistrito");
        List<Asociado> asociados = asociadoRepository.findByIdDistrito(idDistrito);

        return asociados;
    }

    /*
     * Busco un Asoaciado por su ID
     */
    @RequestMapping(value = "/asociados/{idAsociado}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    Asociado getAsociadosById(@PathVariable(value="idAsociado") Integer idAsociado) {
        log.info("getAsociadosByIdDistrito");
        Asociado asociado = asociadoRepository.findByIdAsociado(idAsociado);

        return asociado;
    }

    @RequestMapping("/registration")
    public String registration(Model model) {
        log.info("GET registration");
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute User user) {
        log.info("POST registration");
        log.info("Intentando registrar usuario: " + user.getUsername());

        if(!isUserRegistered(user)) {
            try {
                user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                userRepository.save(user);
                return "registration-success";
            } catch (Error e) {
                return "error";
            }
        } else {
            return "registration-fail";
        }



        // TODO comprobar que se registre y en caso de error actuar


    }

    private boolean isUserRegistered(User user) {
        if(userRepository.findByUsername(user.getUsername()) == null) {
            return false;
        }
        return true;
    }

    @PostMapping("/logged-socio-save")
    public String saveSocioDetails(@ModelAttribute Socio socio) {
        log.info("saveSocioDetails");
        log.info("Guardanto datos de usuario: " + socio.getUsername());

        try {
            socioRepository.save(socio);
        } catch (Error e) {
            return "error";
        }

        // TODO comprobar que se registre y en caso de error actuar

        return "socio-save-success";
    }

    @RequestMapping("/contacts")
    public String contact(Model model) {
        log.info("contacts");
        model.addAttribute("guest", new Guest());
        return "contacts";
    }

    @PostMapping("/contacts")
    public String contactSuccess(@ModelAttribute Guest guest) {
        log.info("POST contacts");
        log.info("Enviando: " + guest.getEmail() + " " + guest.getMessage());
        // mailService.sendContactMail(guest);
        return "contacts-success";
    }

    @RequestMapping("/recover")
    public String passwordRecover(Model model) {
        log.info("passwordRecover");
        model.addAttribute("user", new User());
        return "password-recover";
    }

    @PostMapping("/recover")
    public String userRecoverSuccess(@ModelAttribute User user) {
        log.info("POST recover");

        User recoverUser = userRepository.findByUsername(user.getUsername());
        log.info("Recuperando la contraseña de: " + user.getUsername());
        if(recoverUser != null) {
            log.info("Enviando la contraseña de: " + recoverUser);
            mailService.sendUserPassword(recoverUser);
        }

        return "password-recover-success";
    }

}