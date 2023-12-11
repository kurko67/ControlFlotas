package com.kurko67.controlflotas.models.entity;
import javax.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;


import java.io.Serializable;

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

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    //un conductor, un vehiculo
    @OneToOne(mappedBy = "conductor", cascade = CascadeType.ALL)
    private Vehiculo vehiculo;

    private static final long serialVersionUID = 1L;


}
