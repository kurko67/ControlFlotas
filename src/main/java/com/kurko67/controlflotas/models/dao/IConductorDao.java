package com.kurko67.controlflotas.models.dao;

import com.kurko67.controlflotas.models.entity.Conductor;
import com.kurko67.controlflotas.models.entity.Mantenimiento;
import com.kurko67.controlflotas.models.entity.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface IConductorDao extends PagingAndSortingRepository<Conductor, Long>, CrudRepository<Conductor, Long> {


    @Query(value = "select * from conductores where vencimiento_licencia between current_date and current_date + interval 30 day", nativeQuery = true)
    List<Conductor> findConductoresPorVencerLicencia();






}
