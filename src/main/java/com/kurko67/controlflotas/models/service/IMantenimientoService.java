package com.kurko67.controlflotas.models.service;

import com.kurko67.controlflotas.models.entity.Conductor;
import com.kurko67.controlflotas.models.entity.Mantenimiento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface IMantenimientoService {

    public Page<Mantenimiento> findAll(Pageable pageable);

    public List<Mantenimiento> findAll();

    public List<Mantenimiento> findAllShort();

    public void save(Mantenimiento mantenimiento);
    public Mantenimiento findOne(Long id);
    public void delete(Long id);

}
