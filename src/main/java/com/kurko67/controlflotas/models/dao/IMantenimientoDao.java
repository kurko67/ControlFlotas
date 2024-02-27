package com.kurko67.controlflotas.models.dao;

import com.kurko67.controlflotas.models.entity.Mantenimiento;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface IMantenimientoDao extends PagingAndSortingRepository<Mantenimiento, Long>, CrudRepository<Mantenimiento, Long> {

    @Query(value = "select created_at, descripcion from mantenimientos", nativeQuery = true)
    List<Mantenimiento> findAllShort();

    @Query("FROM Mantenimiento e WHERE e.end > :from AND e.start < :to")
    public List<Object[]> findBetween(@Param("from") @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME) LocalDateTime start, @Param("to") @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME) LocalDateTime end);

    @Query(value = "select id_mantenimiento, text, event_start, event_end from mantenimientos where event_start between ?1 and ?2")
    public List<Mantenimiento> findBetween2(@Param("from") @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME) LocalDateTime start, @Param("to") @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME) LocalDateTime end);



}
