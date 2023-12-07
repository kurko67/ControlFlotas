package com.kurko67.controlflotas.controllers;

import com.kurko67.controlflotas.models.dao.IUsuarioDao;
import com.kurko67.controlflotas.models.entity.Usuario;
import com.kurko67.controlflotas.util.paginator.PageRender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/users")
public class UsuarioController {

    @Autowired
    IUsuarioDao usuariodao;

    @RequestMapping(value = "/list-users")
    public String listaUsuarios(@RequestParam(name="page", defaultValue="0") int page, Model model, @AuthenticationPrincipal User user){


        Pageable pageRequest = PageRequest.of(page, 8);
        Page<Usuario> usuarios = usuariodao.findAll(pageRequest);

        PageRender<Usuario> pageRender = new PageRender<Usuario>("/users/list-users", usuarios);

        model.addAttribute("usuarios", usuarios);
        model.addAttribute("page", pageRender);
        //model.addAttribute("user", user.getUsername());
        return "list-users";

    }


}
