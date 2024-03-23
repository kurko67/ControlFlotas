package com.kurko67.controlflotas.models.dao;

import com.kurko67.controlflotas.models.entity.Notificacion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface INotificacionDao extends CrudRepository<Notificacion, Long> {

    @Query("SELECT n.asunto, n.mensaje FROM Notificacion n")
    List<String> findAllAsuntos();


}
