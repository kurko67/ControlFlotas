package com.kurko67.controlsalones.models.entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name="checklist")
public class CheckList implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idChecklist;
    private Boolean neumaticos;
    private String neumaticosDdetalles;
    private Boolean fluidos;
    private String fluidosDetalles;
    private Boolean luces;
    private String lucesDetalles;
    private Boolean frenos;
    private String frenosDetalles;
    private Boolean trenDelantero;
    private String trenDelanteroDetalles;
    private Boolean seguridad;
    private String seguridadDetalles;
    private Boolean carroceria;
    private String carroceriaDetalles;
    private Boolean documentacion;
    private String documentacionDetalles;
    private String comentariosAdicionales;
    private Date created_at;
    private String tipo_checkList;

    //Relacion muchos checklist a un vehiculo
    @ManyToOne
    @JoinColumn(name = "vehiculo_id")
    private Vehiculo vehiculo;

    //Relacion muchos checklist a un conductor
    @ManyToOne
    @JoinColumn(name = "conductor_id")
    private Conductor conductor;



    private static final long serialVersionUID = 1L;


}
