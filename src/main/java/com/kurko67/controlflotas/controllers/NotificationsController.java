package com.kurko67.controlflotas.controllers;

import com.kurko67.controlflotas.models.dao.INotificacionDao;
import com.kurko67.controlflotas.models.dao.IUsuarioDao;
import com.kurko67.controlflotas.models.entity.Notificacion;
import com.kurko67.controlflotas.models.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


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
    public String ver_notificaciones(@PathVariable(value = "id") Long idNotificacion, Model model){

       Notificacion notificacion = notificacionDao.findNotificacionById(idNotificacion);

       model.addAttribute("notificacion", notificacion);

       return "view-notifications";
    }


}
