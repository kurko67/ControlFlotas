package com.kurko67.controlflotas.models.entity;

import javax.persistence.*;
import lombok.Data;
import lombok.NonNull;


@Data
@Entity
@Table(name="roles")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Forma de generacion de la llave primaria ya que especificamos en la bd q AI
    private Long idRol;

    @NonNull
    private String nombre;

    public Rol(){

    }

    private static final long serialVersionUID = 1L;


}
