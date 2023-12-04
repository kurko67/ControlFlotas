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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/mantenimiento")
public class MantenimientoController {

    @Autowired
    private IVehiculoService vehiculoService;

    @Autowired
    private IMantenimientoService mantenimientoService;

    @PostMapping("/nuevo")
    public String nuevoMantenimiento(@Valid Mantenimiento mantenimiento, @PathVariable(value = "id") Long idVehiculo, BindingResult result, Model model,
                                     RedirectAttributes flash){

        if(result.hasErrors()){
            flash.addFlashAttribute("error", "Error en la carga de datos");
            return "redirect:/view-vehicles/" + idVehiculo;
        }

        Vehiculo vehiculo = vehiculoService.findOne(idVehiculo);
        mantenimiento.setVehiculo(vehiculo);
        mantenimientoService.save(mantenimiento);
        return "redirect:/view-vehicles/" + idVehiculo;

    }



}
