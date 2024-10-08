package com.kurko67.controlsalones.controllers;

import com.kurko67.controlsalones.models.dao.IUsuarioDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class LoginController {

    @Autowired
    private IUsuarioDao usuarioDao;

    @GetMapping("/login")
    public String login(@RequestParam(value="error",required = false ) String error,
                        Model model, Principal principal, RedirectAttributes flash){


        if(principal != null){
            flash.addFlashAttribute("info", "Hola de nuevo: " + principal.getName().toString());
            return "redirect:/";
        }

        if(error != null){
            model.addAttribute("error", "Error: Nombre de usuario o contraseña incorrecta");
        }


        return "login";

    }

    @GetMapping("/register")
    public String register(){
        return "register.html";
    }

}
