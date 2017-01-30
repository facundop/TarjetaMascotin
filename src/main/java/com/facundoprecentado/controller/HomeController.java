package com.facundoprecentado.controller;

import com.facundoprecentado.domain.*;
import com.facundoprecentado.repository.AsociadoRepository;
import com.facundoprecentado.repository.UbicacionRepository;
import com.facundoprecentado.repository.UserRepository;
import com.facundoprecentado.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.SecurityContextProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping("/")
    public String index() {
        log.info("index");
        return "index";
    }

    @RequestMapping("/home")
    public String home(Model model) {
        log.info("home");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        model.addAttribute("user", auth.getPrincipal());
        log.info("Authenticated: " + auth.isAuthenticated());
        log.info("User: " + auth.getPrincipal().toString());
        return "home";
    }

    @RequestMapping("/plans")
    public String plans() {
        log.info("plans");
        return "plans";
    }

    @RequestMapping("/login")
    public String login(Model model) {
        log.info("GET login");
        model.addAttribute("user", new User());
        return "login";
    }

    @RequestMapping(value = "/asociados", method = RequestMethod.GET)
    public String ubicaciones(Model model) {
        log.info("GET ubicaciones");
        List<Ubicacion> departamentos = ubicacionRepository.findDepartamentos();
        model.addAttribute("departamentos", departamentos);
        return "asociados";
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

        for(Ubicacion u : distritos) {
            System.out.println("Distrito: " + u.getIdDistrito() + u.getNombreDistrito());
        }
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
        log.info("registration");
        log.info("Registrando usuario: " + user.getUsername());

        try {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        } catch (Error e) {
            return "error";
        }

        // TODO comprobar que se registre y en caso de error actuar

        return "registration-success";
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

    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    List<User> getClients() {
        log.info("getClients");
        List<User> clients = userRepository.findAll();

        return clients;
    }

}