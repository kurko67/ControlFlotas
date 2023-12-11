package com.kurko67.controlflotas.models.dao;

import com.kurko67.controlflotas.models.entity.Conductor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IConductorDao extends PagingAndSortingRepository<Conductor, Long>, CrudRepository<Conductor, Long> {
}
