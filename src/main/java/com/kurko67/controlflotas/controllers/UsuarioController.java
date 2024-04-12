package com.kurko67.controlflotas.controllers;

import com.kurko67.controlflotas.models.dao.IUsuarioDao;
import com.kurko67.controlflotas.models.entity.Conductor;
import com.kurko67.controlflotas.models.entity.Usuario;
import com.kurko67.controlflotas.models.entity.Vehiculo;
import com.kurko67.controlflotas.models.service.IConductorService;
import com.kurko67.controlflotas.util.paginator.PageRender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
@RequestMapping("/users")
public class UsuarioController {

    @Autowired
    IUsuarioDao usuariodao;

    @Autowired
    IConductorService conductorService;

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

    @RequestMapping("/new")
    public String formVehicles(Model model, @AuthenticationPrincipal User user){

        Usuario usuario = new Usuario();
        model.addAttribute("usuario", usuario);
        return "users";

    }


    @PostMapping(value = "/create_user")
    public String NuevoUser(@Valid Usuario usuario, BindingResult result, RedirectAttributes flash, SessionStatus status,
                            @RequestParam(name = "password") String newPasword,
                            @RequestParam(name = "nombreApellido") String nombreApellido,
                            @RequestParam(name = "cuil") String cuil,
                            @RequestParam(name = "numeroTelefono") String numeroTelefono,
                            @RequestParam(name = "direccion") String direccion,
                            @RequestParam(name = "vencimientoLicencia") String vencimientoLicencia){

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        usuario.setPassword(encoder.encode(newPasword));

        usuario.setHabilitado(true);
        usuariodao.save(usuario);
        Long iduser = usuario.getIdUsuario();
        usuariodao.InsertRol(iduser);

        //Create class conductor

        Conductor conductor = new Conductor();

        conductor.setUsuario(usuario);
        conductor.setNombreApellido(nombreApellido);
        conductor.setCuil(cuil);
        conductor.setNumeroTelefono(numeroTelefono);
        conductor.setDireccion(direccion);
        conductor.setVencimientoLicencia(vencimientoLicencia);

        conductorService.save(conductor);

        status.setComplete();
        flash.addFlashAttribute("success", "¡Usuario " + usuario.getUsername() + " generado con exito!");
        return "redirect:/users/list-users";

    }

    @GetMapping("/edit_user/reset_password/{id}")
    public String editUser(@PathVariable(value = "id") Long id, RedirectAttributes flash, Model model){
        Usuario usuario = null;
        usuario = usuariodao.getOne(id);

        if(usuario.getUsername().equals(null)){
            flash.addFlashAttribute("error", "Usuario no encontrado");
            return "redirect:/users/list-users";
        }else{
            if(id > 0){
                usuariodao.ResetPassword(id);
                flash.addFlashAttribute("success", "La contraseña predeterminada es 123");
                return "redirect:/users/list-users";
            }else{
                flash.addFlashAttribute("error", "Usuario no encontrado");
                return "redirect:/users/list-users";
            }
        }

    }


    @GetMapping("/edit_user/enabledisabled/{id}")
    public String enabledDisabledUsuario(@PathVariable(value = "id") Long id, RedirectAttributes flash){

        Usuario usuario = null;
        usuario = usuariodao.getOne(id);

        if(usuario == null){
            flash.addFlashAttribute("error", "El usuario no existe en la base de datos");
            return "redirect:/users/list-users";
        }

        boolean habilitado;

        if(usuario.isHabilitado() == true){
            habilitado = false;
            flash.addFlashAttribute("warning", "Usuario dado de baja");
        }else{
            habilitado = true;
            flash.addFlashAttribute("success", "Usuario habilitado");
        }


        usuariodao.HabilitarDeshabilitarUser(habilitado,id);
        return "redirect:/users/list-users";

    }


    @GetMapping("profile")
    public String userProfile(Model model, RedirectAttributes flash,
                              @AuthenticationPrincipal User user){

        Usuario usuario = null;
        usuario = usuariodao.findByUsername(user.getUsername());

        if(usuario.equals(null)){
            flash.addFlashAttribute("waring","usuario invalido");
            return "redirect:/";
        }

        Conductor conductor = null;
        conductor = conductorService.findConductorByIdUsuario(usuario.getIdUsuario());
        model.addAttribute("conductor", conductor);

        return "user-profile";
    }


}
