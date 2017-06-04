package com.facundoprecentado.controller;

import com.facundoprecentado.domain.Asociado;
import com.facundoprecentado.domain.Socio;
import com.facundoprecentado.domain.User;
import com.facundoprecentado.repository.SocioRepository;
import com.facundoprecentado.repository.AsociadoRepository;
import com.facundoprecentado.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private SocioRepository socioRepository;

    @Autowired
    private AsociadoRepository asociadoRepository;

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String getAdmin(Model model, Principal principal) {
        List<Socio> socios = socioRepository.findAll();
        List<Asociado> asociados = asociadoRepository.findAll();

        model.addAttribute("socios", socios);
        model.addAttribute("asociados", asociados);

        return "admin";
    }

    @RequestMapping(value = "/admin/asociado/habilitar/{id}", method = RequestMethod.GET)
    public String postHabilitarAsociado(@PathVariable(value="id")  Long id) {
        System.out.println("id: " + id);
        Asociado asociado = asociadoRepository.findById(id);
        asociado.setEnabled(true);
        asociadoRepository.save(asociado);

        return "redirect:/admin";
    }
}