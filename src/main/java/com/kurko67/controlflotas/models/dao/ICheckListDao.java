package com.kurko67.controlflotas.models.dao;

import com.kurko67.controlflotas.models.entity.CheckList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ICheckListDao extends  CrudRepository<CheckList, Long> {



}
