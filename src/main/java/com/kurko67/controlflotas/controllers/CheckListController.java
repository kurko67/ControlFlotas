package com.kurko67.controlflotas.controllers;

import com.kurko67.controlflotas.models.entity.CheckList;
import com.kurko67.controlflotas.models.service.ICheckListService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/checklist")
public class CheckListController {


    @Autowired
    ICheckListService checkListService;

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

}
