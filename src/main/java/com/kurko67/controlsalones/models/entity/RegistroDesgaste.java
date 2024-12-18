package com.kurko67.controlsalones.models.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;


@Data
@Entity
@Table(name="desgaste_cubiertas")
public class RegistroDesgaste implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fechaMedicion;
    private double profundidadRestante; // Profundidad restante del dibujo en mm.
    private double presionActual; // Presión medida en ese momento.

    @ManyToOne
    private Cubierta cubierta;

    private static final long serialVersionUID = 1L;
}
