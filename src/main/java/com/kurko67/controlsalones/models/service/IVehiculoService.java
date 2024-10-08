package com.kurko67.controlsalones.models.service;


import com.kurko67.controlsalones.models.entity.Vehiculo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IVehiculoService {

    public Page<Vehiculo> findAll(Pageable pageable);
    public void save(Vehiculo vehiculo);
    public Vehiculo findOne(Long id);
    public void delete(Long id);
    public boolean existsByPatente(String patente);
    public List<Vehiculo> findVehiculoByVtvExpire();
    public List<Vehiculo> findVehiculoByRutaExpire();
    public List<Vehiculo> findVehiculoBySeguroExpire();
    public List<Object[]> find30daysExpires();
    public Page<Vehiculo> findVehiculoByConductorId(Long conductor_id, Pageable pageable);
    public Page<Vehiculo> findByMarcaContaining(String marca, Pageable pageable);

}
