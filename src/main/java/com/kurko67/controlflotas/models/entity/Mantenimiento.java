package com.kurko67.controlflotas.models.entity;

import javax.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name="mantenimientos")
public class Mantenimiento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Forma de generacion de la llave primaria ya que especificamos en la bd q AI
    private Long idMantenimiento;
    private String tipo; //Preventivo  o Correctivo
    private String descripcion;
    private Double costo;
    private Date created_at;
    private Long dias_antes_alerta;
    private Date fecha_vencimiento;
    private double km;
    //Relacion muchos mantenimientos a un veichulo
    @ManyToOne
    @JoinColumn(name = "vehiculo_id")
    private Vehiculo vehiculo;

    private static final long serialVersionUID = 1L;

}
