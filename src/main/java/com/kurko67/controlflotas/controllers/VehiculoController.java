package com.kurko67.controlflotas.controllers;

import com.kurko67.controlflotas.models.dao.INotificacionDao;
import com.kurko67.controlflotas.models.dao.IUsuarioDao;
import com.kurko67.controlflotas.models.entity.*;
import com.kurko67.controlflotas.models.service.ICheckListService;
import com.kurko67.controlflotas.models.service.IConductorService;
import com.kurko67.controlflotas.models.service.IVehiculoService;
import javax.validation.Valid;

import com.kurko67.controlflotas.models.service.UsuarioService;
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
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes("vehiculo")
@RequestMapping("/vehicles")
public class VehiculoController {

    @Autowired
    private IVehiculoService vehiculoService;

    @Autowired
    private IConductorService conductorService;

     @Autowired
     private IUsuarioDao usuarioService;

     @Autowired
     private ICheckListService checkListService;

    @Autowired
    private INotificacionDao notificacionDao;


    @RequestMapping("/new")
    public String formVehicles(Model model, @AuthenticationPrincipal User user){

        Vehiculo vehiculo = new Vehiculo();
        List<Conductor> conductores = conductorService.findAll();
        model.addAttribute("vehiculo", vehiculo);
        model.addAttribute("conductores", conductores);
        model.addAttribute("titulo", "Cargar nuevo vehiculo ");
        return "vehicles";

    }

    @PostMapping("/nuevo")
    public String crearVehiculo(@Valid Vehiculo vehiculo,BindingResult result, @RequestParam(value = "conductorid") Long idconductor, @RequestParam String patente, Model model, RedirectAttributes flash,
                                @AuthenticationPrincipal User user){


        if(result.hasErrors()){
            flash.addFlashAttribute("danger",  "Error en la carga de datos");
            return "redirect:/vehicles/new";
        }

        if(vehiculoService.existsByPatente(patente) && vehiculo.getIdVehiculo() == null ){
            flash.addFlashAttribute("warning","La patente ingresada ya existe (" + vehiculo.getMarca() +")");
            return "redirect:/vehicles/new";
        }


        // idconductor == 0 > Quiere decir que no se especifico conductor asignado

        if(idconductor > 0){
            Conductor conductor = new Conductor();
            conductor = conductorService.findOne(idconductor);
            vehiculo.setConductor(conductor);
        }

        String mensajeFlash = (vehiculo.getIdVehiculo() != null) ? "Vehiculo " + vehiculo.getMarca() + " editado con exito" : "Vehiculo " + vehiculo.getMarca() + " creado con exito";

        vehiculo.setCreated_at(new Date());
        vehiculo.setUpdated_at(new Date());
        vehiculoService.save(vehiculo);

        flash.addFlashAttribute("success", mensajeFlash);
        return "redirect:/vehicles/list-vehicles";

    }


    @GetMapping("/list-vehicles")
    public String listVehicles(@RequestParam(name="page", defaultValue="0") int page, Model model, @AuthenticationPrincipal User user){

        Pageable pageRequest = PageRequest.of(page, 8);
        Page<Vehiculo> vehiculos = vehiculoService.findAll(pageRequest);

        PageRender<Vehiculo> pageRender = new PageRender<Vehiculo>("/list-vehicles", vehiculos);

        model.addAttribute("vehiculos", vehiculos);
        model.addAttribute("page", pageRender);
        return "list-vehicles";
    }

    @GetMapping("/view-vehicles/{id}")
    public String viewVehicles(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash,
                               @AuthenticationPrincipal User user){

        Vehiculo vehiculo = null;
        vehiculo = vehiculoService.findOne(id);


        if(vehiculo == null || id < 0){
            flash.addFlashAttribute("error", "Vehiculo no econtrado");
            return "redirect:/vehicles/list-vehicles";
        }

        model.addAttribute("vehiculo", vehiculo);
        return "view-vehicles";


    }

    @RequestMapping(value = "/edit-vehicles/{id}")
    public String editar(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash, @AuthenticationPrincipal User user) {

        Vehiculo vehiculo = null;
        vehiculo = vehiculoService.findOne(id);

            if (vehiculo == null || id < 0) {
                flash.addFlashAttribute("error", "Vehiculo no encontrado");
                return "redirect:/vehicles/list-vehicles";
            }


        List<Conductor> conductores = conductorService.findAll();
        model.addAttribute("conductores", conductores);
        model.addAttribute("vehiculo", vehiculo);
        model.addAttribute("titulo", "Editar vehiculo " + vehiculo.getMarca());
        return "vehicles";
    }

    @GetMapping("/my")
    public String mylistVehicles(@RequestParam(name="page", defaultValue="0") int page, Model model, @AuthenticationPrincipal User user){

        Usuario usuario_logueado = usuarioService.findByUsername(user.getUsername());
        Conductor conductor = conductorService.findConductorByIdUsuario(usuario_logueado.getIdUsuario());

        Pageable pageRequest = PageRequest.of(page, 8);
        Page<Vehiculo> vehiculos = vehiculoService.findVehiculoByConductorId(conductor.getIdConductor(), pageRequest);

        PageRender<Vehiculo> pageRender = new PageRender<Vehiculo>("/my", vehiculos);

        model.addAttribute("vehiculos", vehiculos);
        model.addAttribute("page", pageRender);
        return "list-my-vehicles";

    }

    @GetMapping("/checklist/new/{id}")
    public String NewCheckList(Model model,@PathVariable(value = "id") Long id,
                               @AuthenticationPrincipal User user,
                               RedirectAttributes flash){

        CheckList checkList = new CheckList();

        Vehiculo vehiculo = null;
        vehiculo = vehiculoService.findOne(id);

        if (vehiculo == null || id < 0) {
            flash.addFlashAttribute("error", "Vehiculo no encontrado");
            return "redirect:/vehicles/my";
        }

        model.addAttribute("vehiculo", vehiculo);
        model.addAttribute("checklist", checkList);

        return "new-checklist";

    }

    @PostMapping("/checklist/nuevo")
    public String crearCheckList(@Valid CheckList checklist,BindingResult result,
                                 @RequestParam(value = "idVehiculo") Long idVehiculo,
                                 Model model, RedirectAttributes flash,
                                @AuthenticationPrincipal User user){


        if(result.hasErrors()){
            flash.addFlashAttribute("danger",  "Error en la carga de datos");
            return "redirect:/vehicles/my";
        }

        Vehiculo vehiculo = null;
        vehiculo = vehiculoService.findOne(idVehiculo);

        Conductor conductor = null;
        conductor = conductorService.findOne(vehiculo.getConductor().getIdConductor());

        // idconductor == 0 > Quiere decir que no se especifico conductor asignado


        //String mensajeFlash = (vehiculo.getIdVehiculo() != null) ? "Vehiculo " + vehiculo.getMarca() + " editado con exito" : "Vehiculo " + vehiculo.getMarca() + " creado con exito";


        checklist.setCreated_at(new Date());
        checklist.setVehiculo(vehiculo);
        checklist.setConductor(conductor);
        checkListService.save(checklist);

        Notificacion notificacion = new Notificacion();

        //Buscar emisor y definir emisor
        Usuario emisor = usuarioService.findByUsername(user.getUsername());
        notificacion.setEmisor(emisor);

        //esto debe ser momentaneo y se deben mapear todos los usuarios con rol admin y enviar la notificacion a todos
        Integer entero = 1;
        Long largo = entero.longValue();

        Usuario receptor = usuarioService.getOne(largo);
        notificacion.setReceptor(receptor);

        notificacion.setAsunto("Checklist #" + checklist.getIdChecklist());

        notificacion.setMensaje("Te informamos que se ha realizado el control del vehiculo: " + vehiculo.getMarca() + " Dominio: " + vehiculo.getPatente() +
                " Puede ver los resultados desde el Menu Principal, Checklists");
        notificacion.setTipo("NOTIFICACION");
        notificacion.setCreated_at(new Date());

        notificacionDao.save(notificacion);


        flash.addFlashAttribute("success", "Gracias por enviarnos sus comentarios !");
        return "redirect:/vehicles/my";

    }


}
