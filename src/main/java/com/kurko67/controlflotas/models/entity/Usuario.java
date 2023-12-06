package com.kurko67.controlflotas.models.entity;

import javax.persistence.*;
import lombok.Data;
import lombok.NonNull;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name="usuarios")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Forma de generacion de la llave primaria ya que especificamos en la bd q AI
    private Long idUsuario;

    @NonNull
    private String username;

    @NonNull
    private String password;

    @OneToMany
    @JoinColumn(name="id_usuario") // le indicamos cual es la llave foránea cuando generamos la relacion en el Schema

    private List<Rol> roles; //Lista de roles que acepta el usuario

    private boolean habilitado;

    public Usuario(){

    }

    private static final long serialVersionUID = 1L;
}
