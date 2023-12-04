package com.kurko67.controlflotas.controllers;

import com.kurko67.controlflotas.models.entity.Vehiculo;
import com.kurko67.controlflotas.models.service.IVehiculoService;
import javax.validation.Valid;

import com.kurko67.controlflotas.util.paginator.PageRender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;

@Controller
@SessionAttributes("vehiculo")
@RequestMapping("/vehiculo")
public class VehiculoController {

    @Autowired
    private IVehiculoService vehiculoService;


    @RequestMapping("/vehicles/new")
    public String formVehicles(Model model){

        Vehiculo vehiculo = new Vehiculo();
        model.addAttribute("vehiculo", vehiculo);
        return "/vehicles";

    }

    @PostMapping("/nuevo")
    public String crearVehiculo(@Valid Vehiculo vehiculo, BindingResult result, Model model, RedirectAttributes flash){

        if(result.hasErrors()){
            flash.addFlashAttribute("danger",  "Error en la carga de datos");
            return "form-vehiculos";
        }

        //Create object
        vehiculo.setCreated_at(new Date());
        vehiculo.setUpdated_at(new Date());
        vehiculoService.save(vehiculo);

        flash.addFlashAttribute("success", "Vehiculo cargado con éxito");
        return "redirect:/vehiculo/list-vehicles";

    }

    @GetMapping("/listado3")
    public String listadoVehiculos(){
        return "/";
    }


    @GetMapping("/list-vehicles")
    public String listVehicles(@RequestParam(name="page", defaultValue="0") int page, Model model, @AuthenticationPrincipal User user){

        Pageable pageRequest = PageRequest.of(page, 8);
        Page<Vehiculo> vehiculos = vehiculoService.findAll(pageRequest);

        PageRender<Vehiculo> pageRender = new PageRender<Vehiculo>("/list-vehicles", vehiculos);

        model.addAttribute("vehiculos", vehiculos);
        model.addAttribute("page", pageRender);
        //model.addAttribute("user", user.getUsername());
        return "list-vehicles";
    }

    @GetMapping("/view-vehicles/{id}")
    public String viewVehicles(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash){

        Vehiculo vehiculo = null;
        vehiculo = vehiculoService.findOne(id);


        if(vehiculo == null || id < 0){
            flash.addFlashAttribute("error", "Vehiculo no econtrado");
            return "redirect:/vehiculo/list-vehicles";
        }

        model.addAttribute("vehiculo", vehiculo);
        return "view-vehicles";
    }

}
