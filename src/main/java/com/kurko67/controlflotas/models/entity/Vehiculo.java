package com.kurko67.controlflotas.models.entity;

import javax.persistence.*;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Data
@Entity
@Table(name="vehiculos")
public class Vehiculo implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVehiculo;
    private String tipo; // Camion, camioneta, auto, truck
    private String marca;
    private Long anio;
    private String patente;
    private Long km;
    private String estado;
    private String ubicacion;
    private Date created_at;
    private Date updated_at;

    //Un vehiculo muchos mantenimientos
    @OneToMany(mappedBy = "vehiculo", cascade = CascadeType.ALL)
    private List<Mantenimiento> mantenimientos;

    @OneToOne
    @JoinColumn(name = "conductor_id", unique = true)
    private Conductor conductor;

    private static final long serialVersionUID = 1L;

}
