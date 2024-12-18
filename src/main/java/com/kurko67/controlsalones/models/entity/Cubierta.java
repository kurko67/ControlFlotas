package com.kurko67.controlsalones.models.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name="cubiertas")
public class Cubierta implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo; // Código único para identificar la cubierta.
    private String posicion; // Ej: "Delantera derecha", "Trasera izquierda".
    private String marca;
    private String modelo;
    private String medida;

    private double presionRecomendada; // Presión ideal.
    private double profundidadRecomendada; // Profundidad del dibujo en mm.

    @ManyToOne
    private Vehiculo vehiculo;

    @OneToMany(mappedBy = "cubierta", cascade = CascadeType.ALL)
    private List<RegistroDesgaste> registrosDesgaste;


    private static final long serialVersionUID = 1L;


}
