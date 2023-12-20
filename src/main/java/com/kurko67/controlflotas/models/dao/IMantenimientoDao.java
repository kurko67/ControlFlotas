package com.kurko67.controlflotas.models.dao;

import com.kurko67.controlflotas.models.entity.Mantenimiento;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface IMantenimientoDao extends PagingAndSortingRepository<Mantenimiento, Long>, CrudRepository<Mantenimiento, Long> {

    @Query(value = "select created_at, descripcion from mantenimientos", nativeQuery = true)
    List<Mantenimiento> findAllShort();


}
