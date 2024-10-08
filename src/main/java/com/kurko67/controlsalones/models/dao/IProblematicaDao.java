package com.kurko67.controlsalones.models.dao;


import com.kurko67.controlsalones.models.entity.Problematicas;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface IProblematicaDao  extends PagingAndSortingRepository<Problematicas, Long>, CrudRepository<Problematicas, Long> {

    @Query(value = "SELECT * FROM problematicas WHERE checklist_id = ?1", nativeQuery = true)
    List<Problematicas> findProblematicaById(Long id);

    @Query(value = "SELECT * FROM problematicas WHERE checklist_id = ?1 limit 1", nativeQuery = true)
    Problematicas findOneProblematicaByIdCheck(Long id);

    @Query(value = "SELECT * FROM problematicas WHERE checklist_id = ?1 and check_temp = 'SI'", nativeQuery = true)
    List<Problematicas> findCheckTempSiByCheckListId(Long id);

    @Modifying
    @Transactional
    @Query(value = "update problematicas set estado = 'RESUELTO', fh_finalizado = current_date, mantenimiento_id = ?1 where checklist_id = ?2 and check_temp = 'SI'", nativeQuery = true)
    void UpdateCheckListOt(Long mantenimiento_id, Long checklist_id);



}
