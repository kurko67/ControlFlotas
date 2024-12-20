package com.kurko67.controlsalones.models.service;

import com.kurko67.controlsalones.models.dao.IMantenimientoDao;
import com.kurko67.controlsalones.models.entity.Mantenimiento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class IMantenimientoServiceImpl implements IMantenimientoService{


    @Autowired
    IMantenimientoDao mantenimientoDao;

    @Override
    @Transactional(readOnly = true)
    public Page<Mantenimiento> findAll(Pageable pageable) {
        return mantenimientoDao.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Page<Mantenimiento> findMantenimientosByConductorId(Long conductor_id, String estado, Pageable pageable) {
        return mantenimientoDao.findMantenimientosByConductorId(conductor_id, estado,pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Mantenimiento> findAll() {
        return (List<Mantenimiento>) mantenimientoDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Mantenimiento> findAllShort() {
        return (List<Mantenimiento>) mantenimientoDao.findAllShort();
    }

    @Override
    @Transactional
    public void save(Mantenimiento mantenimiento) {
        mantenimientoDao.save(mantenimiento);
    }

    @Override
    public Mantenimiento findOne(Long id) {
        return mantenimientoDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        mantenimientoDao.deleteById(id);
    }

    @Override
    public BigDecimal findGastosMensuales() {
        return mantenimientoDao.findGastosMensuales();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> obtenerMantenimientos() {
        return (List<Object[]>) mantenimientoDao.obtenerMantenimientos();
    }
}
