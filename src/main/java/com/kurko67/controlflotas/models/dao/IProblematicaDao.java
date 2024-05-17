package com.kurko67.controlflotas.models.dao;


import com.kurko67.controlflotas.models.entity.Problematicas;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface IProblematicaDao  extends PagingAndSortingRepository<Problematicas, Long>, CrudRepository<Problematicas, Long> {

    @Query(value = "SELECT * FROM problematicas WHERE checklist_id = ?1", nativeQuery = true)
    List<Problematicas> findProblematicaById(Long id);

    @Query(value = "SELECT * FROM problematicas WHERE checklist_id = ?1 and check_temp = 'SI'", nativeQuery = true)
    List<Problematicas> findCheckTempSiByCheckListId(Long id);

}
