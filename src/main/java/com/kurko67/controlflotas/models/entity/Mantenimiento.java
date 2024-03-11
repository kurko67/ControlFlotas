package com.kurko67.controlflotas.models.entity;

import javax.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private String text;
    private Double costo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date created_at;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha_realizacion;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "event_start")
    LocalDateTime start;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "event_end")
    LocalDateTime end;
    //Relacion muchos mantenimientos a un veichulo
    @ManyToOne
    @JoinColumn(name = "vehiculo_id")
    private Vehiculo vehiculo;

    private String estado; // activo, finalizado
    private String categoriaAveria;
    private String subCategoriaAveria;

    //Relacion muchos mantenimientos a un veichulo
    @ManyToOne
    @JoinColumn(name = "conductor_id")
    private Conductor conductor;

    public Mantenimiento() {

    }



    private static final long serialVersionUID = 1L;

}
