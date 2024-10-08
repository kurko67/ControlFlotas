package com.kurko67.controlsalones.models.entity;
import javax.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;


import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name="conductores")
public class Conductor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idConductor;
    private String nombreApellido;
    private String cuil;
    private String numeroTelefono;
    private String direccion;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String vencimientoLicencia;

    // Relacion uno  a uno con usuario

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;


    // Relación uno a muchos con Vehiculo
    @OneToMany(mappedBy = "conductor", cascade = CascadeType.ALL)
    private List<Vehiculo> vehiculos;

    // Relación uno a muchos con CheckList
    @OneToMany(mappedBy = "conductor", cascade = CascadeType.ALL)
    private List<CheckList> checkLists;

    private static final long serialVersionUID = 1L;


}
