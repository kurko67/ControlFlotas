package com.kurko67.controlflotas.models.service;

import com.kurko67.controlflotas.models.entity.Problematicas;
import com.kurko67.controlflotas.models.entity.Vehiculo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProblematicaService {

    public Page<Problematicas> findAll(Pageable pageable);
    public void save(Problematicas problematicas);
    public Problematicas findOne(Long id);
    public void delete(Long id);

}
