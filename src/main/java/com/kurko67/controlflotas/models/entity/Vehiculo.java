package com.kurko67.controlflotas.models.entity;

import javax.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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
    private String tipo; // Camion, camioneta, auto, truck, maquina agricola
    private String marca;
    private Long anio;
    private String patente;
    private Long km;
    private String estado;
    private String ubicacion;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date vencimientoVtv;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date vencimientoRuta;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date vencimientoSeguro;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date created_at;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updated_at;

    //Un vehiculo muchos mantenimientos
    @OneToMany(mappedBy = "vehiculo", cascade = CascadeType.ALL)
    private List<Mantenimiento> mantenimientos;

    // Relación muchos a uno con Conductor
    @ManyToOne
    @JoinColumn(name = "conductor_id")
    private Conductor conductor;

    private static final long serialVersionUID = 1L;

}
