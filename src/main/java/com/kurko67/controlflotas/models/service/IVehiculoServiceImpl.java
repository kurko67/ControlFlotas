package com.kurko67.controlflotas.models.service;

import com.kurko67.controlflotas.models.dao.IVehiculoDao;
import com.kurko67.controlflotas.models.entity.Vehiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
