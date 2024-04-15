package com.kurko67.controlflotas.models.service;

import com.kurko67.controlflotas.models.entity.Conductor;
import com.kurko67.controlflotas.models.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface IConductorService {

    public Page<Conductor> findAll(Pageable pageable);
    public List<Conductor> findAll();
    public void save(Conductor conductor);
    public Conductor findOne(Long id);
    public void delete(Long id);
    public List<Conductor> findConductoresPorVencerLicencia();

    public Conductor findConductorByIdUsuario(Long idUsuario);



}