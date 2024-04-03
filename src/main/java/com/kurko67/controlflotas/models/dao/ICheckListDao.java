package com.kurko67.controlflotas.models.dao;

import com.kurko67.controlflotas.models.entity.CheckList;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ICheckListDao  extends PagingAndSortingRepository<CheckList, Long>,CrudRepository<CheckList, Long> {

    @Query(value = "select * from checklist where id_checklist = ?1", nativeQuery = true)
    CheckList findCheckListById(Long id_checklist);

}
