package com.kurko67.controlflotas.controllers;

import com.kurko67.controlflotas.models.dao.INotificacionDao;
import com.kurko67.controlflotas.models.dao.IUsuarioDao;
import com.kurko67.controlflotas.models.entity.Notificacion;
import com.kurko67.controlflotas.models.entity.Usuario;
import com.kurko67.controlflotas.models.entity.Vehiculo;
import com.kurko67.controlflotas.util.paginator.PageRender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.*;

@Controller
public class NotificationsController {

    @Autowired
    private INotificacionDao notificacionDao;

    @Autowired
    IUsuarioDao usuariodao;

    @GetMapping("/notificaciones")
    @ResponseBody
    public Map<Long, String> mostrarNotificaciones(@AuthenticationPrincipal User user) {

        Usuario receptor = usuariodao.findByUsername(user.getUsername());
        Pageable pageable = PageRequest.of(0, 5); // Obtener las primeras 5 notificaciones

        List<Object[]> asuntosYMensajes = notificacionDao.findLastFiveByReceptor(receptor, pageable);
        Map<Long, String> notificaciones = new HashMap<>();
        for (Object[] asuntoYMensaje : asuntosYMensajes) {
            Long id = (Long) asuntoYMensaje[0]; // Suponiendo que el ID está en la primera posición del arreglo
            String asunto = (String) asuntoYMensaje[1];
            notificaciones.put(id, asunto);
        }
        return notificaciones;
    }

    @GetMapping("/view-notifications/{id}")
    public String ver_notificaciones(@PathVariable(value = "id") Long idNotificacion, Model model,
                                     @AuthenticationPrincipal User user, RedirectAttributes flash){

        String rol = "";

        for(int i=0; i < user.getAuthorities().size(); i++){
            rol = user.getAuthorities().toString();
        }

       Notificacion notificacion = notificacionDao.findNotificacionById(idNotificacion);

       if(notificacion == null || idNotificacion < 0){
           flash.addFlashAttribute("error", "No se encuentra notificacion");
           if(rol.equals("[ROLE_ADMIN]")){
               return "redirect:/vehicles/list-vehicles";
           }else{
               return "redirect:/vehicles/my";
           }
        }


       model.addAttribute("notificacion", notificacion);

       return "view-notifications";
    }


    @GetMapping("/list-notifications")
    public String ver_todas_notificaciones(@RequestParam(name="page", defaultValue="0") int page, Model model, @AuthenticationPrincipal User user){

        Usuario receptor = usuariodao.findByUsername(user.getUsername());

        Pageable pageRequest = PageRequest.of(page, 8);
        Page<Notificacion> pageNotificaciones = notificacionDao.findAllByReceptor(receptor, pageRequest);

        List<Notificacion> notificaciones = pageNotificaciones.getContent(); // Obtener la lista de notificaciones de la página actual

        System.out.println(notificaciones);

        PageRender<Notificacion> pageRender = new PageRender<Notificacion>("/list-notifications", pageNotificaciones);

        model.addAttribute("notificaciones", notificaciones);
        model.addAttribute("page", pageRender);
        return "list-notifications";
    }




}
