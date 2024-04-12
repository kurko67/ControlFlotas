package com.kurko67.controlflotas.models.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "notificaciones")
public class Notificacion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNotificacion;

    @ManyToOne
    @JoinColumn(name = "emisor_id")
    private Usuario emisor;

    @ManyToOne
    @JoinColumn(name = "receptor_id")
    private Usuario receptor;

    private String asunto;
    @Column(length = 500)
    private String mensaje;

    private String tipo; //por ejemplo, mensaje, alerta, recordatorio, orden_trabajo

    private Date created_at;


    private static final long serialVersionUID = 1L;
}
