package com.kurko67.controlflotas.models.service;

import com.kurko67.controlflotas.models.dao.IConductorDao;
import com.kurko67.controlflotas.models.entity.Conductor;
import com.kurko67.controlflotas.models.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class IConductorServiceImpl implements IConductorService{

    @Autowired
    IConductorDao conductorDao;

    @Override
    @Transactional(readOnly = true)
    public Page<Conductor> findAll(Pageable pageable) {
        return conductorDao.findAll(pageable);
    }

    @Override
    public List<Conductor> findAll() {
        return (List<Conductor>) conductorDao.findAll();
    }

    @Override
    public List<Conductor> findConductoresPorVencerLicencia() {
        return (List<Conductor>) conductorDao.findConductoresPorVencerLicencia();
    }

    @Override
    @Transactional()
    public void save(Conductor conductor) {
        conductorDao.save(conductor);
    }

    @Override
    @Transactional(readOnly = true)
    public Conductor findOne(Long id) {
        return conductorDao.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Conductor findConductorByIdUsuario(Long idUsuario) {
        return conductorDao.findConductorByIdUsuario(idUsuario);
    }

    @Override
    @Transactional()
    public void delete(Long id) {
        conductorDao.deleteById(id);
    }




}
