package com.kurko67.controlsalones.models.service;

import com.kurko67.controlsalones.models.dao.IVehiculoDao;
import com.kurko67.controlsalones.models.entity.Vehiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IVehiculoServiceImpl implements IVehiculoService{

    @Autowired
    private IVehiculoDao vehiculoDao;

    @Override
    @Transactional(readOnly = true)
    public Page<Vehiculo> findAll(Pageable pageable) {
        return vehiculoDao.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Vehiculo> findVehiculoByVtvExpire() {
        return (List<Vehiculo>) vehiculoDao.findVehiculoByVtvExpire();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Vehiculo> findVehiculoByRutaExpire() {
        return (List<Vehiculo>) vehiculoDao.findVehiculoByRutaExpire();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Vehiculo> findVehiculoBySeguroExpire() {
        return (List<Vehiculo>) vehiculoDao.findVehiculoBySeguroExpire();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> find30daysExpires() {
        return (List<Object[]>) vehiculoDao.find30daysExpires();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Vehiculo> findVehiculoByConductorId(Long conductor_id, Pageable pageable) {
        return (Page<Vehiculo>) vehiculoDao.findVehiculoByConductorId(conductor_id, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Vehiculo> findByMarcaContaining(String marca, Pageable pageable) {
        return (Page<Vehiculo>) vehiculoDao.findByMarcaContaining(marca, pageable);
    }


    @Override
    @Transactional
    public void save(Vehiculo vehiculo) {
         vehiculoDao.save(vehiculo);
    }

    @Override
    @Transactional(readOnly = true)
    public Vehiculo findOne(Long id) {
        return vehiculoDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        vehiculoDao.deleteById(id);
    }

    @Override
    public boolean existsByPatente(String patente) {
        return vehiculoDao.existsByPatente(patente);
    }
}
