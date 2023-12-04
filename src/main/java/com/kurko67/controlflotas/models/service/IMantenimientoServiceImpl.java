package com.kurko67.controlflotas.models.service;

import com.kurko67.controlflotas.models.dao.IVehiculoDao;
import com.kurko67.controlflotas.models.entity.Mantenimiento;
import com.kurko67.controlflotas.models.entity.Vehiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public class IMantenimientoServiceImpl implements IMantenimientoService{


    @Autowired
    IMantenimientoService mantenimientoService;

    @Override
    @Transactional(readOnly = true)
    public Page<Mantenimiento> findAll(Pageable pageable) {
        return mantenimientoService.findAll(pageable);
    }

    @Override
    @Transactional
    public void save(Mantenimiento mantenimiento) {
        mantenimientoService.save(mantenimiento);
    }

    @Override
    public Mantenimiento findOne(Long id) {
        return mantenimientoService.findOne(id);
    }

    @Override
    public void delete(Long id) {
        mantenimientoService.delete(id);
    }
}
