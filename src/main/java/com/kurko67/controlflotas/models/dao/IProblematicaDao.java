package com.kurko67.controlflotas.models.dao;


import com.kurko67.controlflotas.models.entity.Problematicas;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IProblematicaDao  extends PagingAndSortingRepository<Problematicas, Long>, CrudRepository<Problematicas, Long> {

}
