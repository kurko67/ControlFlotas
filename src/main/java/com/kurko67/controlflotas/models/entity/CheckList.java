package com.kurko67.controlflotas.models.entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name="checklist")
public class CheckList implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idChecklist;
    private boolean neumaticos;
    private boolean fluidos;
    private boolean luces;
    private boolean frenos;
    private boolean suspension;
    private boolean seguridad;
    private boolean carroceria;
    private boolean documentacion;
    private String comentarios;
    private Date created_at;

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
