package com.kurko67.controlflotas.models.dao;

import com.kurko67.controlflotas.models.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface IUsuarioDao extends JpaRepository<Usuario, Long> {

    Usuario findByUsername(String username);

    @Modifying
    @Transactional
    @Query(value = "update usuario set password = ?1 where id_usuario = ?2", nativeQuery = true)
    void UpdatePassword(String password, Long idUsuario);

    @Modifying
    @Transactional
    @Query(value = "insert into rol(nombre, id_usuario) values ('ROLE_USER', :valor1)", nativeQuery = true)
    void InsertRol(@Param("valor1") Long idUsuario);

    @Modifying
    @Transactional
    @Query(value = "delete from rol where id_usuario = :valor1 ", nativeQuery = true)
    void DeleteRol(@Param("valor1") Long id);

    @Modifying
    @Transactional
    @Query(value = "update usuario set habilitado = :valor1 where id_usuario = :valor2 ", nativeQuery = true)
    void HabilitarDeshabilitarUser(@Param("valor1") boolean habilitado,@Param("valor2") Long id);


}
