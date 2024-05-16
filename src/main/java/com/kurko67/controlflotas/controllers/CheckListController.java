package com.kurko67.controlflotas.controllers;

import com.kurko67.controlflotas.models.entity.CheckList;
import com.kurko67.controlflotas.models.entity.Problematicas;
import com.kurko67.controlflotas.models.service.ICheckListService;
import com.kurko67.controlflotas.models.service.IProblematicaService;
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

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/checklist")
public class CheckListController {


    @Autowired
    ICheckListService checkListService;

    @Autowired
    IProblematicaService problematicaService;

    @GetMapping("/list-checklist")
    public String viewallchecklist(@RequestParam(name="page", defaultValue="0") int page, Model model, @AuthenticationPrincipal User user){

        Pageable pageRequest = PageRequest.of(page, 8);
        Page<CheckList> checkLists = checkListService.findAll(pageRequest);

        PageRender<CheckList> pageRender = new PageRender<CheckList>("/list-checklist", checkLists);

        model.addAttribute("checkLists", checkLists);
        model.addAttribute("page", pageRender);

        return "list-checklist";
    }

    @GetMapping("/view-checklist/{id}")
    public String viewCheckListByid(@PathVariable(value = "id") Long idChecklist, Model model,
                                    @AuthenticationPrincipal User user, RedirectAttributes flash) {

        CheckList checkList = null;
        checkList = checkListService.findCheckListById(idChecklist);

        if (checkList == null || idChecklist < 0) {

            flash.addFlashAttribute("warning", "Checklist no encontrado");
            return "redirect:/checklist/list-checklist";

        }

        model.addAttribute("checkList", checkList);
        return "view-checklist";

    }

    @GetMapping("/generate/ot/{id}")
    public String generateOtFromCheckList(@PathVariable(value = "id") Long idChecklist, Model model,
                                    @AuthenticationPrincipal User user, RedirectAttributes flash) {

        CheckList checkList = null;
        checkList = checkListService.findCheckListById(idChecklist);

        if(checkList == null){
            flash.addFlashAttribute("warning", "Checklist no encontrado");
            return "redirect:/view-checklist";
        }

        List<Problematicas> problematicas = problematicaService.findProblematicaById(checkList.getIdChecklist());
        model.addAttribute("problematicas", problematicas);

        System.out.println(problematicas.get(1).getIdProblematica());


        return "ot-from-checklist";

    }

    @GetMapping("/actualizarestado/{id}")
    @ResponseBody
    public String actualizarEstadoCheck(@PathVariable(value = "id") Long idProblematica) {

        // Buscar la problemática en la base de datos
        Problematicas problematicas = problematicaService.findOne(idProblematica);

        if (problematicas != null) {
            // Obtener el estado actual
            String estado = problematicas.getCheckTemp();

            // Cambiar el estado
            if (estado.equals("SI")) {
                problematicas.setCheckTemp("NO");
            } else {
                problematicas.setCheckTemp("SI");
            }

            // Guardar la problemática actualizada
            problematicaService.save(problematicas);

            return "Estado actualizado correctamente";
        } else {
            return "Error: La problemática no existe";
        }
    }



}
