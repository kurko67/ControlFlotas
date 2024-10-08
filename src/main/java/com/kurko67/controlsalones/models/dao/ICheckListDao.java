package com.kurko67.controlsalones.models.dao;

import com.kurko67.controlsalones.models.entity.CheckList;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ICheckListDao  extends PagingAndSortingRepository<CheckList, Long>,CrudRepository<CheckList, Long> {

    @Query(value = "select * from checklist where id_checklist = ?1", nativeQuery = true)
    CheckList findCheckListById(Long id_checklist);

    @Query(value = "select count(*) from checklist where neumaticos = 0 and created_at between current_date and current_date + interval 30 day", nativeQuery = true)
    Integer ContarNeumaticos();

    @Query(value = "select count(*) from checklist where fluidos = 0 and created_at between current_date and current_date + interval 30 day", nativeQuery = true)
    Integer ContarFluidos();

    @Query(value = "select count(*) from checklist where luces = 0 and created_at between current_date and current_date + interval 30 day", nativeQuery = true)
    Integer ContarLuces();

    @Query(value = "select count(*) from checklist where frenos = 0 and created_at between current_date and current_date + interval 30 day", nativeQuery = true)
    Integer ContarFrenos();

    @Query(value = "select count(*) from checklist where tren_delantero = 0 and created_at between current_date and current_date + interval 30 day", nativeQuery = true)
    Integer ContarTrenDelantero();

    @Query(value = "select count(*) from checklist where seguridad = 0 and created_at between current_date and current_date + interval 30 day", nativeQuery = true)
    Integer ContarSeguridad();

    @Query(value = "select count(*) from checklist where carroceria = 0 and created_at between current_date and current_date + interval 30 day", nativeQuery = true)
    Integer ContarCarroceria();

    @Query(value = "select count(*) from checklist where documentacion = 0 and created_at between current_date and current_date + interval 30 day", nativeQuery = true)
    Integer ContarDocumentacion();

}

