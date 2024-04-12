package com.kurko67.controlflotas.controllers;

import com.kurko67.controlflotas.models.dao.INotificacionDao;
import com.kurko67.controlflotas.models.dao.IUsuarioDao;
import com.kurko67.controlflotas.models.entity.Conductor;
import com.kurko67.controlflotas.models.entity.Vehiculo;
import com.kurko67.controlflotas.models.service.IConductorService;
import com.kurko67.controlflotas.models.service.IVehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller()
public class DashBoardController {

    @Autowired
    private IConductorService conductorService;
    @Autowired
    private IVehiculoService vehiculoService;

    @GetMapping("/")
    public String panelAdministrador(Model model, RedirectAttributes flash){

        List<Conductor> conductores = conductorService.findConductoresPorVencerLicencia();
        List<Object[]> vehiculos = vehiculoService.find30daysExpires();

        model.addAttribute("conductores", conductores);
        model.addAttribute("vehiculos", vehiculos);

        return "index";

    }

    @GetMapping("/events")
    public String viewCalendar(Model model){

        return "calendar";

    }

}
