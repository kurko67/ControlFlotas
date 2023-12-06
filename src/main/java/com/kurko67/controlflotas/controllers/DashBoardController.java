package com.kurko67.controlflotas.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller()
public class DashBoardController {


    @GetMapping("/")
    public String panelAdministrador(Model model, RedirectAttributes flash){

        return "index";

    }

}
