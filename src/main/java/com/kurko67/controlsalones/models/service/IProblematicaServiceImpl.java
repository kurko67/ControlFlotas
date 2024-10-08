package com.kurko67.controlsalones.models.service;

import com.kurko67.controlsalones.models.dao.IProblematicaDao;
import com.kurko67.controlsalones.models.entity.Problematicas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IProblematicaServiceImpl implements IProblematicaService{

    @Autowired
    IProblematicaDao problematicaDao;
    @Override
    @Transactional(readOnly = true)
    public Page<Problematicas> findAll(Pageable pageable) {
        return problematicaDao.findAll(pageable);
    }

    @Override
    @Transactional
    public void save(Problematicas problematicas) {
        problematicaDao.save(problematicas);
    }

    @Override
    @Transactional(readOnly = true)
    public Problematicas findOne(Long id) {
        return problematicaDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        problematicaDao.deleteById(id);
    }

    @Override
    public List<Problematicas> findProblematicaById(Long id) {
        return (List<Problematicas>) problematicaDao.findProblematicaById(id);
    }

    @Override
    public List<Problematicas> findCheckTempSiByCheckListId(Long id) {
        return (List<Problematicas>) problematicaDao.findCheckTempSiByCheckListId(id);
    }

    @Override
    public void UpdateCheckListOt(Long mantenimiento_id, Long checklist_id) {
        problematicaDao.UpdateCheckListOt(mantenimiento_id, checklist_id);
    }

    @Override
    @Transactional(readOnly = true)
    public Problematicas findOneProblematicaByIdCheck(Long id) {
        return problematicaDao.findOneProblematicaByIdCheck(id);
    }

}
