package com.kurko67.controlflotas.models.service;

import com.kurko67.controlflotas.models.entity.Vehiculo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IVehiculoService {

    public Page<Vehiculo> findAll(Pageable pageable);
    public void save(Vehiculo vehiculo);
    public Vehiculo findOne(Long id);
    public void delete(Long id);


}
