package com.kurko67.controlsalones.models.service;

import com.kurko67.controlsalones.models.entity.Problematicas;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProblematicaService {

    public Page<Problematicas> findAll(Pageable pageable);
    public void save(Problematicas problematicas);
    public Problematicas findOne(Long id);
    public void delete(Long id);
    public List<Problematicas> findProblematicaById(Long id);
    public List<Problematicas> findCheckTempSiByCheckListId(Long id);
    public void UpdateCheckListOt(Long mantenimiento_id, Long checklist_id);
    public Problematicas findOneProblematicaByIdCheck(Long id);

}
