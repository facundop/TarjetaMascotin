package com.facundoprecentado.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.facundoprecentado.domain.Asociado;
import com.facundoprecentado.domain.Socio;
import com.facundoprecentado.repository.AsociadoRepository;
import com.facundoprecentado.repository.SocioRepository;

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