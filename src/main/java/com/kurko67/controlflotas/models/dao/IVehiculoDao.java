package com.kurko67.controlflotas.models.dao;

import com.kurko67.controlflotas.models.entity.Vehiculo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVehiculoDao extends PagingAndSortingRepository<Vehiculo, Long>, CrudRepository<Vehiculo, Long> {



}
