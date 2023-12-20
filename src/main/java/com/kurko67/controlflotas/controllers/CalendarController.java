package com.kurko67.controlflotas.controllers;

import com.kurko67.controlflotas.models.entity.Mantenimiento;
import com.kurko67.controlflotas.models.service.IMantenimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/calendar")
public class CalendarController {

    @Autowired
    IMantenimientoService mantenimientoService;

    @GetMapping("/view")
    public String viewCalendar(Model model){

        List<Mantenimiento> mantenimientos = mantenimientoService.findAllShort();
        System.out.println("mantenimientos :" + mantenimientos );
        model.addAttribute("mantenimientos", mantenimientos);
        return "calendar";

    }


}
