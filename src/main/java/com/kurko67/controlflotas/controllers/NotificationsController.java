package com.kurko67.controlflotas.controllers;

import com.kurko67.controlflotas.models.dao.INotificacionDao;
import com.kurko67.controlflotas.models.entity.Notificacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.List;

@RestController
public class NotificationsController {

    @Autowired
    private INotificacionDao notificacionDao;

    @GetMapping("/notificaciones")
    public List<String> mostrarNotificaciones() {
        List<String> asuntos = notificacionDao.findAllAsuntos();
        return asuntos;
    }



}
