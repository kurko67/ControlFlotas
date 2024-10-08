package com.kurko67.controlsalones.models.service;

import com.kurko67.controlsalones.models.entity.Mantenimiento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;


public interface IMantenimientoService {

    public Page<Mantenimiento> findAll(Pageable pageable);
    public Page<Mantenimiento> findMantenimientosByConductorId(Long conductor_id, String estado, Pageable pageable);
    public List<Mantenimiento> findAll();
    public List<Mantenimiento> findAllShort();
    public void save(Mantenimiento mantenimiento);
    public Mantenimiento findOne(Long id);
    public void delete(Long id);
    BigDecimal findGastosMensuales();
    public List<Object[]> obtenerMantenimientos();

}
