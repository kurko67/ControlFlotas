package com.kurko67.controlsalones.models.entity;

import javax.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name="mantenimientos")
public class Mantenimiento implements Serializable {

    //Esta clase en realidad es una "orden de trabajo"

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Forma de generacion de la llave primaria ya que especificamos en la bd q AI
    private Long idMantenimiento;
    private String tipo; //Preventivo  o Correctivo
    @Column(length = 500)
    private String descripcion_problema;
    private String text; //Solo para el calendario
    private Double costo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime created_at;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha_realizacion;

    @Column(length = 500)
    private String descripcion_mantenimiento;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "event_start")
    LocalDateTime start;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "event_end")
    LocalDateTime end; // solo para calendar
    //Relacion muchos mantenimientos a un veichulo
    @ManyToOne
    @JoinColumn(name = "vehiculo_id")
    private Vehiculo vehiculo;

    private String estado; // activo, finalizado
    private String categoriaAveria;
    private String subCategoriaAveria;
    private String lugar_atencion;

    //Relacion muchos mantenimientos a un conductor
    @ManyToOne
    @JoinColumn(name = "conductor_id")
    private Conductor conductor;

    @ManyToOne
    @JoinColumn(name = "emisor_id")
    private Usuario emisor;

    public Mantenimiento() {

    }



    private static final long serialVersionUID = 1L;

}
