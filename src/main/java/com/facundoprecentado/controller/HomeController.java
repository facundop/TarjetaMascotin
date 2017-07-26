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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private MailService mailService;

    @Autowired
    private UserRepository userRepository;

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
        List<Asociado> asociados = asociadoRepository.findAsociadosByEnabled(true);
        model.addAttribute("asociados", asociados);

        if(principal != null) { // Esta logeado
            log.info("Usuario logeado: " + principal.getName());
        }

        return "home";
    }

    @RequestMapping("/plans")
    public String plans() {
        return "plans";
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
//    @RequestMapping(value = "/asociados/byDistrito/{idDistrito}", method = RequestMethod.GET, produces = "application/json")
//    public @ResponseBody
//    List<Asociado> getAsociadosByIdDistrito(@PathVariable(value="idDistrito") Integer idDistrito) {
//        log.info("getAsociadosByIdDistrito");
//        List<Asociado> asociados = asociadoRepository.findByIdDistrito(idDistrito);
//
//        return asociados;
//    }

    @PostMapping("/logged-socio-save")
    public String saveSocioDetails(@ModelAttribute Socio socio) {
        Socio temp = socioRepository.findByUsername(socio.getUsername());

        if(temp != null) { // Ya existe en la tabla de socios, no tengo que crear uno
            socio.setId(temp.getId());
        }

        try {
            socioRepository.save(socio);
        } catch (Error e) {
            return "error";
        }

        return "socio-save-success";
    }

    @PostMapping("/logged-asociado-save")
    public String saveAsociadoDetails(@ModelAttribute Asociado asociado) {
        Asociado temp = asociadoRepository.findByUsername(asociado.getUsername());

        if(temp != null) { // Ya existe en la tabla de asociado, no tengo que crear uno
            asociado.setId(temp.getId());
        }

        try {
            asociadoRepository.save(asociado);
        } catch (Error e) {
            return "error";
        }

        return "asociado-save-success";
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
        mailService.sendContactMail(guest);
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