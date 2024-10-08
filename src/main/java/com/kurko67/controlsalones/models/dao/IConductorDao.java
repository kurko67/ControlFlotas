package com.kurko67.controlsalones.models.dao;

import com.kurko67.controlsalones.models.entity.Conductor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface IConductorDao extends PagingAndSortingRepository<Conductor, Long>, CrudRepository<Conductor, Long> {


    @Query(value = "select * from conductores where vencimiento_licencia between current_date and current_date + interval 30 day", nativeQuery = true)
    List<Conductor> findConductoresPorVencerLicencia();

    @Query(value = "select * from conductores where usuario_id = ?1", nativeQuery = true)
    Conductor findConductorByIdUsuario(Long idUsuario);






}
