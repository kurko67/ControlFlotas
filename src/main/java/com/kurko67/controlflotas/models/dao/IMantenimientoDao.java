package com.kurko67.controlflotas.models.dao;

import com.kurko67.controlflotas.models.entity.Mantenimiento;
import com.kurko67.controlflotas.models.entity.Vehiculo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface IMantenimientoDao extends PagingAndSortingRepository<Mantenimiento, Long>, CrudRepository<Mantenimiento, Long> {

    @Query(value = "select created_at, descripcion from mantenimientos", nativeQuery = true)
    List<Mantenimiento> findAllShort();

    @Query("select e.idMantenimiento, e.text, e.start, e.end FROM Mantenimiento e WHERE e.end > :from AND e.start < :to")
    public List<Object[]> findBetween(@Param("from") LocalDateTime start, @Param("to") @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME) LocalDateTime end);

    @Query(value = "select * from mantenimientos where conductor_id = ?1 and estado = ?2 order by estado DESC", nativeQuery = true)
    Page<Mantenimiento> findMantenimientosByConductorId(Long conductor_id, String estado, Pageable pageable);

    @Query(value = "select sum(costo) from mantenimientos where fecha_realizacion between current_date and current_date + interval 30 day", nativeQuery = true)
    BigDecimal findGastosMensuales();

}
