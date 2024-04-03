package com.kurko67.controlflotas.models.service;

import com.kurko67.controlflotas.models.entity.CheckList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICheckListService {


    public Page<CheckList> findAll(Pageable pageable);
    public void save(CheckList checkList);
    public boolean findOne(Long id);
    public void delete(Long id);


}
