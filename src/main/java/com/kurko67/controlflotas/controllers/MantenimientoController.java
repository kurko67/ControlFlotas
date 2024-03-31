package com.kurko67.controlflotas.controllers;

import com.kurko67.controlflotas.models.dao.INotificacionDao;
import com.kurko67.controlflotas.models.dao.IUsuarioDao;
import com.kurko67.controlflotas.models.entity.*;
import com.kurko67.controlflotas.models.service.IConductorService;
import com.kurko67.controlflotas.models.service.IMantenimientoService;
import com.kurko67.controlflotas.models.service.IVehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat.DATE_TIME;

@Controller
@RequestMapping("/maintenance")
public class MantenimientoController {

    @Autowired
    private IVehiculoService vehiculoService;

    @Autowired
    private IMantenimientoService mantenimientoService;

    @Autowired
    private IConductorService conductorService;

    @Autowired
    private INotificacionDao notificacionDao;

    @Autowired
    private IUsuarioDao usuarioDao;

    @RequestMapping("/new/{id}")
    public String formVehicles(@PathVariable(value = "id") Long idVehiculo, Model model,
                               @AuthenticationPrincipal User user){

        List<Conductor> conductores = conductorService.findAll();
        Vehiculo vehiculo  = vehiculoService.findOne(idVehiculo);
        Mantenimiento mantenimiento = new Mantenimiento();
        model.addAttribute("mantenimiento", mantenimiento);
        model.addAttribute("vehiculo", vehiculo);
        model.addAttribute("conductores", conductores);
        return "maintenance";

    }


    @PostMapping("/nuevo")
    public String newMaintenance(@Valid Mantenimiento mantenimiento, @RequestParam Long idVehiculo,@RequestParam Long conductor,  @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start, BindingResult result, Model model,
                                     RedirectAttributes flash,@AuthenticationPrincipal User user){



        if(result.hasErrors()){
            flash.addFlashAttribute("error", "Error en la carga de datos");
            return "redirect:/view-vehicles/" + idVehiculo;
        }

        System.out.println(user);

        Vehiculo vehiculo = vehiculoService.findOne(idVehiculo);
        mantenimiento.setText(vehiculo.getPatente());
        mantenimiento.setEstado("ACTIVO");
        mantenimiento.setCreated_at(new Date());
        mantenimiento.setVehiculo(vehiculo);
        mantenimiento.setStart(start);
        mantenimiento.setEnd(start);
        mantenimientoService.save(mantenimiento);

        Notificacion notificacion = new Notificacion();

        //Buscar emisor y definir emisor
        Usuario emisor = usuarioDao.findByUsername(user.getUsername());
        notificacion.setEmisor(emisor);

        //buscar conductor usuario y definir receptor
        Conductor conductor_id = conductorService.findOne(conductor);
        Usuario receptor = conductor_id.getUsuario();
        notificacion.setReceptor(receptor);

        notificacion.setAsunto("Nueva Orden de Trabajo");

        notificacion.setMensaje("Te hemos asignado un mantenimiento '" + mantenimiento.getTipo() + "'. Para el vehiculo: " + vehiculo.getMarca() +
                " " + vehiculo.getAnio() + ", DOMINIO: " + vehiculo.getPatente() +
                "  * Categoria:  " + mantenimiento.getCategoriaAveria() +
                "  Orientacion del problema:  " + mantenimiento.getSubCategoriaAveria() +
                " *** Mas detalles: *** " + mantenimiento.getText() +
                " *** Fecha programada: *** " + mantenimiento.getStart());
        notificacion.setTipo("ORDEN_TRABAJO");
        notificacion.setCreated_at(new Date());

        notificacionDao.save(notificacion);

        return "redirect:/vehicles/view-vehicles/" + idVehiculo;

    }

    @GetMapping("/view-maintenance/{id}")
    public String viewMaintenanceById(@PathVariable(value = "id") Long idMaintenance, Model model,
                                  @AuthenticationPrincipal User user, RedirectAttributes flash){

        Mantenimiento mantenimiento = null;
        mantenimiento = mantenimientoService.findOne(idMaintenance);

        if(mantenimiento == null || idMaintenance < 0){
            flash.addFlashAttribute("error", "No se encuentra mantenimiento");
            return "redirect:/vehicles/list-vehicles";
        }

        model.addAttribute("mantenimiento", mantenimiento);
        return "view-maintenance";
    }


    @GetMapping("/modal-maintenance/{id}")
    public String viewMaintenanceByIdModal(@PathVariable(value = "id") Long idMaintenance, Model model,
                                           @AuthenticationPrincipal User user, RedirectAttributes flash){

        Mantenimiento mantenimiento = null;
        mantenimiento = mantenimientoService.findOne(idMaintenance);

        if(mantenimiento == null || idMaintenance < 0){
            flash.addFlashAttribute("error", "No se encuentra mantenimiento");
            return "redirect:/events";
        }

        model.addAttribute("mantenimiento", mantenimiento);
        return "layout/modal_calendar";
    }




}
