package com.kurko67.controlsalones.models.service;

import com.kurko67.controlsalones.models.entity.CheckList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ICheckListService {


    public Page<CheckList> findAll(Pageable pageable);
    public void save(CheckList checkList);
    public Optional<CheckList> findOne(Long id);
    public void delete(Long id);
    public CheckList findCheckListById(Long id_checklist);
    Integer ContarNeumaticos();
    Integer ContarFluidos();
    Integer ContarLuces();
    Integer ContarFrenos();
    Integer ContarTrenDelantero();
    Integer ContarSeguridad();
    Integer ContarCarroceria();
    Integer ContarDocumentacion();


}
