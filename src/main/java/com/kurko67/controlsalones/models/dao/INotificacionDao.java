package com.kurko67.controlsalones.models.dao;

import com.kurko67.controlsalones.models.entity.Notificacion;
import com.kurko67.controlsalones.models.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface INotificacionDao extends CrudRepository<Notificacion, Long> {

    @Query("SELECT n.idNotificacion, n.asunto FROM Notificacion n WHERE n.receptor = :receptor ORDER BY n.created_at DESC")
    List<Object[]> findLastFiveByReceptor(@Param("receptor") Usuario receptor, Pageable pageable);

    @Query("SELECT n FROM Notificacion n WHERE n.receptor = :receptor ORDER BY n.created_at DESC")
    Page<Notificacion> findAllByReceptor(@Param("receptor") Usuario receptor, Pageable pageable);

    @Query("SELECT n.idNotificacion, n.asunto FROM Notificacion n")
    List<Object[]> findallNotifications();

    @Query("SELECT n FROM Notificacion n WHERE n.idNotificacion = ?1")
    Notificacion findNotificacionById(Long id);




}
