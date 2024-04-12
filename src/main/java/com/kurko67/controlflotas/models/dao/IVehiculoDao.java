package com.kurko67.controlflotas.models.dao;

import com.kurko67.controlflotas.models.entity.Conductor;
import com.kurko67.controlflotas.models.entity.Vehiculo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IVehiculoDao extends PagingAndSortingRepository<Vehiculo, Long>, CrudRepository<Vehiculo, Long> {

    boolean existsByPatente(String patente);

    /* notificaciones */

    @Query(value = "select * from vehiculos where vencimiento_vtv between current_date and current_date + interval 30 day", nativeQuery = true)
    List<Vehiculo> findVehiculoByVtvExpire();

    @Query(value = "select * from vehiculos where vencimiento_ruta between current_date and current_date + interval 30 day", nativeQuery = true)
    List<Vehiculo> findVehiculoByRutaExpire();

    @Query(value = "select * from vehiculos where vencimiento_seguro between current_date and current_date + interval 30 day", nativeQuery = true)
    List<Vehiculo> findVehiculoBySeguroExpire();

    @Query(value = "SELECT id_vehiculo, marca, patente, 'VTV' as 'tipo_vencimiento', vencimiento_vtv as 'vencimiento' FROM vehiculos WHERE vencimiento_vtv between current_date and current_date + interval 30 day " +
            " UNION SELECT id_vehiculo, marca, patente, 'SEGURO' as 'tipo_vencimiento', vencimiento_seguro as 'vencimiento' FROM vehiculos WHERE vencimiento_seguro between current_date and current_date + interval 30 day " +
            " UNION SELECT id_vehiculo, marca, patente, 'RUTA' as 'tipo_vencimiento', vencimiento_ruta as 'vencimiento' FROM vehiculos WHERE vencimiento_ruta between current_date and current_date + interval 30 day", nativeQuery = true)
    List<Object[]> find30daysExpires();

    /* Section users */
    /* find vehicles list of users */

    @Query(value = "select * from vehiculos where conductor_id = ?1", nativeQuery = true)
    Page<Vehiculo> findVehiculoByConductorId(Long conductor_id, Pageable pageable);


}
