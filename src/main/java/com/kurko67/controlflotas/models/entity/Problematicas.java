package com.kurko67.controlflotas.models.entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "problematicas")
public class Problematicas implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProblematica;

    private String tipo; //neumatico, luces, ect
    private String detalle_problema;
    private String estado; //puede ser resuelto o pendiente
    private String checkTemp; // Se utiliza para saber si esta elegido momentaneamente para la realizacion de la OT


    private Date created_at;
    private Date fh_finalizado;

    @ManyToOne
    @JoinColumn(name = "vehiculo_id")
    private Vehiculo vehiculo;

    @ManyToOne
    @JoinColumn(name = "checklist_id")
    private CheckList checkList;

    //relacion uno a uno con Mantenimiento (orden de trabajo)
    @OneToOne
    @JoinColumn(name = "mantenimiento_id")
    private Mantenimiento mantenimiento;




}
