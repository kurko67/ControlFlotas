package com.kurko67.controlflotas.controllers;

import com.kurko67.controlflotas.models.dao.INotificacionDao;
import com.kurko67.controlflotas.models.dao.IUsuarioDao;
import com.kurko67.controlflotas.models.entity.*;
import com.kurko67.controlflotas.models.service.*;
import com.kurko67.controlflotas.util.paginator.PageRender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import java.util.Map;

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
    private IUsuarioDao usuarioService;

    @Autowired
    private IProblematicaService problematicaService;


    private TwilioWhatsAppService twilioWhatsAppService;


    private WhatsAppService whatsAppService;



    @Autowired
    public MantenimientoController( TwilioWhatsAppService twilioWhatsAppService,
                                    WhatsAppService whatsAppService) {
        this.twilioWhatsAppService = twilioWhatsAppService;
        this.whatsAppService = whatsAppService;
    }


    //parametro idchecklist es opcional
    @RequestMapping("/new/{id}/{idchecklist?}")
    public String formVehicles(@PathVariable(value = "id") Long idVehiculo, @PathVariable(value = "idchecklist", required = false) Long idchecklist, Model model,
                               @AuthenticationPrincipal User user){

        if(idchecklist != null){
            List<Problematicas> problematicas = problematicaService.findCheckTempSiByCheckListId(idchecklist);
            model.addAttribute("problematicas",problematicas );
        }

        List<Conductor> conductores = conductorService.findAll();
        Vehiculo vehiculo  = vehiculoService.findOne(idVehiculo);
        Mantenimiento mantenimiento = new Mantenimiento();
        model.addAttribute("conductorSeleccionado", vehiculo.getConductor().getIdConductor());
        model.addAttribute("mantenimiento", mantenimiento);
        model.addAttribute("vehiculo", vehiculo);
        model.addAttribute("conductores", conductores);
        return "maintenance";

    }


    @GetMapping("/obtener-telefono-conductor")
    @ResponseBody
    public ResponseEntity<?> obtenerTelefonoConductor(@RequestParam Long conductorId) {
        Conductor conductor = conductorService.findOne(conductorId);
        if (conductor != null) {
            String telefono = conductor.getNumeroTelefono();
            return ResponseEntity.ok().body(Map.of("telefono", telefono));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conductor no encontrado");
        }
    }


    @PostMapping("/nuevo")
    public String newMaintenance(@Valid Mantenimiento mantenimiento, @RequestParam Long idVehiculo,@RequestParam Long conductor,  @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start, BindingResult result, Model model,
                                     RedirectAttributes flash,@AuthenticationPrincipal User user){



        if(result.hasErrors()){
            flash.addFlashAttribute("error", "Error en la carga de datos");
            return "redirect:/view-vehicles/" + idVehiculo;
        }

        Notificacion notificacion = new Notificacion();

        //Buscar emisor
        Usuario emisor = usuarioService.findByUsername(user.getUsername());
        notificacion.setEmisor(emisor);

        Vehiculo vehiculo = vehiculoService.findOne(idVehiculo);
        mantenimiento.setText(vehiculo.getPatente());
        mantenimiento.setEstado("ACTIVO");
        mantenimiento.setCreated_at(new Date());
        mantenimiento.setVehiculo(vehiculo);
        mantenimiento.setStart(start);
        mantenimiento.setEnd(start);
        mantenimiento.setEmisor(emisor);
        mantenimientoService.save(mantenimiento);




        //buscar conductor usuario y definir receptor
        Conductor conductor_id = conductorService.findOne(conductor);
        Usuario receptor = conductor_id.getUsuario();
        notificacion.setReceptor(receptor);

        notificacion.setAsunto("Nueva Orden de Trabajo");

        notificacion.setMensaje("Te hemos asignado un mantenimiento '" + mantenimiento.getTipo() + "'. Para el vehiculo: " + vehiculo.getMarca() +
                " " + vehiculo.getAnio() + ", DOMINIO: " + vehiculo.getPatente() +
                " *** Categoría:  " + mantenimiento.getCategoriaAveria() +
                " -- Sub Categoría:  " + mantenimiento.getSubCategoriaAveria() +
                " *** Mas detalles: *** " + mantenimiento.getDescripcion_problema() +
                " *** Fecha programada: *** " + mantenimiento.getStart() +
                "*** Lugar de atención: ***" + mantenimiento.getLugar_atencion());
        notificacion.setTipo("ORDEN_TRABAJO");
        notificacion.setCreated_at(new Date());

        notificacionDao.save(notificacion);

        //twilioWhatsAppService.sendMessage();

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

    @GetMapping("/my/ot/{id}")
    public String viewMyOt(@PathVariable(value = "id") Long idMaintenance, Model model,
                                      @AuthenticationPrincipal User user, RedirectAttributes flash){

        Mantenimiento mantenimiento = null;
        mantenimiento = mantenimientoService.findOne(idMaintenance);

        if(mantenimiento == null || idMaintenance < 0){
            flash.addFlashAttribute("error", "No se encuentra mantenimiento");
            return "redirect:/vehicles/list-vehicles";
        }

        model.addAttribute("mantenimiento", mantenimiento);
        return "view-my-maintenance";
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


    @RequestMapping(value = "/my")
    public String MyMaintenance(@RequestParam(name="page", defaultValue="0") int page,
                                    @RequestParam(name = "estado", defaultValue = "ACTIVO") String estado,
                                    Model model, @AuthenticationPrincipal User user){


        System.out.println(estado);

        Usuario usuario_logueado = usuarioService.findByUsername(user.getUsername());
        Conductor conductor = conductorService.findConductorByIdUsuario(usuario_logueado.getIdUsuario());

        Pageable pageRequest = PageRequest.of(page, 8);
        Page<Mantenimiento> mantenimientos = mantenimientoService.findMantenimientosByConductorId(conductor.getIdConductor(), estado, pageRequest);

        PageRender<Mantenimiento> pageRender = new PageRender<Mantenimiento>("/my", mantenimientos);

        model.addAttribute("mantenimientos", mantenimientos);
        model.addAttribute("page", pageRender);
        return "list-my-ot";


    }


    @GetMapping("/obtener-telefono-emisor")
    @ResponseBody
    public ResponseEntity<?> obtenerTelefonoEmisor(@RequestParam Long idMantenimiento) {
        System.out.println("id desde spring: " + idMantenimiento);
        Mantenimiento mantenimiento = mantenimientoService.findOne(idMantenimiento);
        if (mantenimiento != null) {
            Long idEmisor = mantenimiento.getEmisor().getIdUsuario();
            System.out.println("Emisor que envio el OT: " + idEmisor);
            Conductor conductor = conductorService.findConductorByIdUsuario(idEmisor);
            System.out.println("Entonces el emisor es: " + conductor.getNombreApellido());
            return ResponseEntity.ok().body(Map.of(
                    "telefono", conductor.getNumeroTelefono(),
                    "nombre", conductor.getNombreApellido(),
                    "conductor", mantenimiento.getConductor().getNombreApellido(),
                    "vehiculo", mantenimiento.getVehiculo().getMarca()
            ));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conductor no encontrado");
        }
    }

    @PostMapping("/update")
    public String FinalizeMaintenance(@Valid Mantenimiento mantenimiento, @RequestParam Long idMantenimiento,
                                      @RequestParam String descripcion_mantenimiento,
                                      @RequestParam Double costo, BindingResult result,
                                      @RequestParam(value = "km") Long km, Model model,
                                 RedirectAttributes flash,@AuthenticationPrincipal User user){


        mantenimiento = mantenimientoService.findOne(idMantenimiento);


        if(result.hasErrors()){
            flash.addFlashAttribute("error", "Error en la carga de datos");
            return "redirect:/vehicles/my";
        }

        Vehiculo vehiculo = null;
        vehiculo = mantenimiento.getVehiculo();

        if (vehiculo == null){
            flash.addFlashAttribute("error", "Error en la carga de datos");
            return "redirect:/vehicles/my";
        }

        mantenimiento.setEstado("FINALIZADO");
        mantenimiento.setDescripcion_mantenimiento(descripcion_mantenimiento);
        mantenimiento.setCosto(costo);
        mantenimiento.setFecha_realizacion(new Date());
        mantenimientoService.save(mantenimiento);

        vehiculo.setKm(km);
        vehiculoService.save(vehiculo);

        Notificacion notificacion = new Notificacion();

        //Buscar emisor
        Usuario emisor = usuarioService.findByUsername(user.getUsername());
        notificacion.setEmisor(emisor);

        //Se envia el que genero la orden de trabajo, ya que agregue al modelo Mantenimiento el Emisor

        Usuario receptor = mantenimiento.getEmisor();
        notificacion.setReceptor(receptor);

        notificacion.setAsunto("Fin orden de Trabajo");

        notificacion.setMensaje("Te informamos que se ha finalizado la ORDEN DE TRABAJO N°: " + mantenimiento.getIdMantenimiento() +
                " , del vehiculo: " + mantenimiento.getVehiculo().getMarca() + " Dominio: " + mantenimiento.getVehiculo().getPatente() +
                " ****** Descripcion del mantenimiento: " + mantenimiento.getDescripcion_mantenimiento()
                + " ******* Costo del servicio: $" + mantenimiento.getCosto());
        notificacion.setTipo("NOTIFICACION");
        notificacion.setCreated_at(new Date());

        notificacionDao.save(notificacion);

        return "redirect:/vehicles/my";

    }





}
