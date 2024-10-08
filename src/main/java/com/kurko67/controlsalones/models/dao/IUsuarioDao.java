package com.kurko67.controlsalones.models.dao;

import com.kurko67.controlsalones.models.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface IUsuarioDao extends JpaRepository<Usuario, Long> {

    Usuario findByUsername(String username);

    Page<Usuario> findAll(Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "update usuarios set password = ?1 where id_usuario = ?2", nativeQuery = true)
    void UpdatePassword(String password, Long idUsuario);

    @Modifying
    @Transactional
    @Query(value = "update usuarios set password = '$2a$10$561esWd5wPWJ.8ocbqMJnuZ4RmVPUTdPPpJMt4b5qnZ6wD9rM/Wn2' where id_usuario = ?1", nativeQuery = true)
    void ResetPassword(Long idUsuario);

    @Modifying
    @Transactional
    @Query(value = "insert into roles(nombre, id_usuario) values ('ROLE_USER', :valor1)", nativeQuery = true)
    void InsertRol(@Param("valor1") Long idUsuario);

    @Modifying
    @Transactional
    @Query(value = "delete from roles where id_usuario = :valor1 ", nativeQuery = true)
    void DeleteRol(@Param("valor1") Long id);

    @Modifying
    @Transactional
    @Query(value = "update usuarios set habilitado = :valor1 where id_usuario = :valor2 ", nativeQuery = true)
    void HabilitarDeshabilitarUser(@Param("valor1") boolean habilitado,@Param("valor2") Long id);

    boolean existsByUsername(String username);

}
