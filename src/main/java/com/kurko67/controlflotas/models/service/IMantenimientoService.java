package com.kurko67.controlflotas.models.service;

import com.kurko67.controlflotas.models.entity.Mantenimiento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IMantenimientoService {

    public Page<Mantenimiento> findAll(Pageable pageable);
    public void save(Mantenimiento mantenimiento);
    public Mantenimiento findOne(Long id);
    public void delete(Long id);

}
