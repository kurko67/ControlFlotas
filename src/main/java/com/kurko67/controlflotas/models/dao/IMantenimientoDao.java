package com.kurko67.controlflotas.models.dao;

import com.kurko67.controlflotas.models.entity.Mantenimiento;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IMantenimientoDao extends PagingAndSortingRepository<Mantenimiento, Long>, CrudRepository<Mantenimiento, Long> {

}
