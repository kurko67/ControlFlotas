package com.kurko67.controlsalones.controllers;

import com.kurko67.controlsalones.models.dao.INotificacionDao;
import com.kurko67.controlsalones.models.dao.IUsuarioDao;
import com.kurko67.controlsalones.models.dao.IVehiculoDao;
import com.kurko67.controlsalones.models.entity.*;
import com.kurko67.controlsalones.models.service.*;

import javax.validation.Valid;

import com.kurko67.controlsalones.util.paginator.PageRender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

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

    @Autowired
    IProblematicaService problematicaService;

    @Autowired
    IVehiculoDao vehiculoDao;


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
    public String crearVehiculo(
            @Valid Vehiculo vehiculo,
            BindingResult result,
            @RequestParam(value = "conductorid", required = false) Long idConductor,
            @RequestParam String patente,
            Model model,
            RedirectAttributes flash,
            @AuthenticationPrincipal User user) {

        if (result.hasErrors()) {
            flash.addFlashAttribute("danger", "Error en la carga de datos");
            return "redirect:/vehicles/new";
        }

        if (vehiculoService.existsByPatente(patente) && vehiculo.getIdVehiculo() == null) {
            flash.addFlashAttribute("warning", "La patente ingresada ya existe (" + vehiculo.getMarca() + ")");
            return "redirect:/vehicles/new";
        }

        // Asociar conductor al vehículo si se especificó
        if (idConductor != null && idConductor > 0) {
            Conductor conductor = conductorService.findOne(idConductor);
            if (conductor != null) {
                vehiculo.setConductor(conductor);
            }
        }

        // Asociar cubiertas al vehículo
        if (vehiculo.getCubiertas() != null) {
            for (Cubierta cubierta : vehiculo.getCubiertas()) {
                cubierta.setVehiculo(vehiculo);
            }
        }

        // Guardar el vehículo (y sus cubiertas gracias a Cascade)
        vehiculo.setCreated_at(new Date());
        vehiculo.setUpdated_at(new Date());
        vehiculoService.save(vehiculo);

        String mensajeFlash = (vehiculo.getIdVehiculo() != null) ?
                "Vehiculo " + vehiculo.getMarca() + " editado con éxito" :
                "Vehiculo " + vehiculo.getMarca() + " creado con éxito";

        flash.addFlashAttribute("success", mensajeFlash);
        return "redirect:/vehicles/list-vehicles";
    }


    @GetMapping("/list-vehicles")
    public String listVehicles(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "marca", required = false, defaultValue = "") String marca,
            Model model,
            @AuthenticationPrincipal User user
    ) {
        Pageable pageRequest = PageRequest.of(page, 8);
        Page<Vehiculo> vehiculosPage;

        if (marca.isEmpty()) {
            vehiculosPage = vehiculoService.findAll(pageRequest);
        } else {
            vehiculosPage = vehiculoService.findByMarcaContaining(marca, pageRequest);
        }

        PageRender<Vehiculo> pageRender = new PageRender<>("/list-vehicles", vehiculosPage);

        model.addAttribute("vehiculos", vehiculosPage);
        model.addAttribute("page", pageRender);
        model.addAttribute("marca", marca); // Para que el filtro de búsqueda mantenga el valor
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
                                 @RequestParam(value = "km") Long km,
                                 Model model, RedirectAttributes flash,
                                @AuthenticationPrincipal User user){


        if(result.hasErrors()){
            flash.addFlashAttribute("danger",  "Error en la carga de datos");
            return "redirect:/vehicles/my";
        }

        Vehiculo vehiculo = null;
        vehiculo = vehiculoService.findOne(idVehiculo);
        vehiculo.setKm(km);
        vehiculoService.save(vehiculo);

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

        persistirRespuestasNo(checklist, vehiculo);

        flash.addFlashAttribute("success", "Gracias por enviarnos sus comentarios !");
        return "redirect:/vehicles/my";

    }

    private void persistirRespuestasNo(CheckList checklist, Vehiculo vehiculo) {
        try {
            // Obtener todos los campos de la clase CheckList
            Field[] fields = checklist.getClass().getDeclaredFields();

            // Iterar sobre cada campo
            for (Field field : fields) {
                field.setAccessible(true);

                // Verificar si el campo es de tipo booleano
                if (field.getType().equals(Boolean.class) || field.getType().equals(boolean.class)) {
                    // Obtener el valor del campo
                    boolean fieldValue = (boolean) field.get(checklist);

                    // Si el valor es false, guardar los detalles en Problematicas
                    if (!fieldValue) {
                        // Obtener los detalles correspondientes
                        String detalles = obtenerDetalles(field.getName(), checklist);
                        String tipos = obtenerTipo(field.getName());
                        guardarRegistroProblematicas(tipos, detalles, vehiculo, checklist);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    // Función para guardar un registro en Problematicas
    private void guardarRegistroProblematicas(String tipos, String detalles, Vehiculo vehiculo, CheckList checklist) {
        Problematicas problematicas = new Problematicas();
        problematicas.setVehiculo(vehiculo);
        problematicas.setCheckList(checklist);
        problematicas.setEstado("PENDIENTE");
        problematicas.setCheckTemp("SI");
        problematicas.setTipo(tipos);
        problematicas.setCreated_at(new Date());
        problematicas.setDetalle_problema(detalles); // Establecer el detalle de la respuesta "NO"
        problematicaService.save(problematicas); // Guardar el registro en la base de datoss
    }

    private String obtenerDetalles(String fieldName, CheckList checklist) {
        // Implementa la lógica para obtener los detalles según el nombre del campo
        // Puedes definir un mapa u otro método para mapear los nombres de los campos con sus detalles
        // Por ejemplo:
        switch (fieldName) {
            case "neumaticos":
                return checklist.getNeumaticosDdetalles();
            case "fluidos":
                return checklist.getFluidosDetalles();
            case "luces":
                return checklist.getLucesDetalles();
            case "frenos":
                return checklist.getFrenosDetalles();
            case "trenDelantero":
                return checklist.getTrenDelanteroDetalles();
            case "seguridad":
                return checklist.getSeguridadDetalles();
            case "carroceria":
                return checklist.getCarroceriaDetalles();
            case "documentacion":
                return checklist.getDocumentacionDetalles();
            default:
                return "";
        }
    }

    private String obtenerTipo(String fieldName) {
        // Implementa la lógica para obtener los detalles según el nombre del campo
        // Puedes definir un mapa u otro método para mapear los nombres de los campos con sus detalles
        // Por ejemplo:
        switch (fieldName) {
            case "neumaticos":
                return "neumaticos";
            case "fluidos":
                return "fluidos";
            case "luces":
                return "luces";
            case "frenos":
                return "frenos";
            case "trenDelantero":
                return "trenDelantero";
            case "seguridad":
                return "seguridad";
            case "carroceria":
                return "carroceria";
            case "documentacion":
                return "documentacion";
            default:
                return "";
        }
    }

    @GetMapping("/buscar")
    public String buscar(
            @RequestParam(name = "marca", required = false, defaultValue = "") String marca,
            @PageableDefault(size = 10) Pageable pageable,
            Model model
    ) {
        Page<Vehiculo> vehiculosPage = vehiculoService.findByMarcaContaining(marca, pageable);

        PageRender<Vehiculo> pageRender = new PageRender<Vehiculo>("/list-vehicles", vehiculosPage);

        model.addAttribute("vehiculos", vehiculosPage);
        model.addAttribute("page", pageRender);
        return "layout/data-list-vehicle :: data-table-vehicles";
    }

}
