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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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


}
