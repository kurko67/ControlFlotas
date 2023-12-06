package com.kurko67.controlflotas.controllers;

import com.kurko67.controlflotas.models.entity.Mantenimiento;
import com.kurko67.controlflotas.models.entity.Vehiculo;
import com.kurko67.controlflotas.models.service.IMantenimientoService;
import com.kurko67.controlflotas.models.service.IVehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Date;

@Controller
@RequestMapping("/maintenance")
public class MantenimientoController {

    @Autowired
    private IVehiculoService vehiculoService;

    @Autowired
    private IMantenimientoService mantenimientoService;

    @RequestMapping("/new/{id}")
    public String formVehicles(@PathVariable(value = "id") Long idVehiculo, Model model){

        Vehiculo vehiculo  = vehiculoService.findOne(idVehiculo);
        Mantenimiento mantenimiento = new Mantenimiento();
        model.addAttribute("mantenimiento", mantenimiento);
        model.addAttribute("vehiculo", vehiculo);
        return "maintenance";

    }


    @PostMapping("/nuevo")
    public String nuevoMantenimiento(@Valid Mantenimiento mantenimiento, @RequestParam Long idVehiculo, BindingResult result, Model model,
                                     RedirectAttributes flash){

        System.out.println(idVehiculo);

        if(result.hasErrors()){
            flash.addFlashAttribute("error", "Error en la carga de datos");
            return "redirect:/view-vehicles/" + idVehiculo;
        }

        Vehiculo vehiculo = vehiculoService.findOne(idVehiculo);
        mantenimiento.setCreated_at(new Date());
        mantenimiento.setVehiculo(vehiculo);
        mantenimientoService.save(mantenimiento);
        return "redirect:/vehicles/view-vehicles/" + idVehiculo;

    }



}
