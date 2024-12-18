package com.kurko67.controlsalones.models.dao;

import com.kurko67.controlsalones.models.entity.Cubierta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICubiertasDao extends JpaRepository<Cubierta, Long> {



}
