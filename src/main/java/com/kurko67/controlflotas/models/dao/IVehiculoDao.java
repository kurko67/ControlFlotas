package com.kurko67.controlflotas.models.dao;

import com.kurko67.controlflotas.models.entity.Conductor;
import com.kurko67.controlflotas.models.entity.Vehiculo;
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

    @Query(value = "SELECT id, vehiculo, 'VTV' AS tipo_vencimiento, vencimiento_vtv AS fecha_vencimiento " +
            "FROM tu_tabla " +
            "WHERE vencimiento_vtv BETWEEN CURRENT_DATE AND CURRENT_DATE + INTERVAL 30 DAY", nativeQuery = true)
    List<Vehiculo> findExpires();


}
