package com.kurko67.controlflotas.models.dao;

import com.kurko67.controlflotas.models.entity.CheckList;
import com.kurko67.controlflotas.models.entity.Vehiculo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ICheckListDao extends  PagingAndSortingRepository<CheckList, Long>, CrudRepository<CheckList, Long> {



}
