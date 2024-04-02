package com.kurko67.controlflotas.models.service;

import com.kurko67.controlflotas.models.entity.CheckList;

import java.util.List;

public interface ICheckListService {


    public List<CheckList> findAll();
    public void save(CheckList checkList);
    public boolean findOne(Long id);
    public void delete(Long id);


}
