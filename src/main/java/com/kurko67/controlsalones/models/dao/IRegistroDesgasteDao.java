package com.kurko67.controlsalones.models.dao;


import com.kurko67.controlsalones.models.entity.RegistroDesgaste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRegistroDesgasteDao extends JpaRepository<RegistroDesgaste, Long> {



}
