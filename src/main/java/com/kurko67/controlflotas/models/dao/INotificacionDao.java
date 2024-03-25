package com.kurko67.controlflotas.models.dao;

import com.kurko67.controlflotas.models.entity.Notificacion;
import com.kurko67.controlflotas.models.entity.Usuario;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface INotificacionDao extends CrudRepository<Notificacion, Long> {

    @Query("SELECT n.idNotificacion, n.asunto FROM Notificacion n WHERE n.receptor = :receptor ORDER BY n.created_at DESC")
    List<Object[]> findLastFiveByReceptor(@Param("receptor") Usuario receptor, Pageable pageable);

    @Query("SELECT n.idNotificacion, n.asunto FROM Notificacion n")
    List<Object[]> findallNotifications();


}
