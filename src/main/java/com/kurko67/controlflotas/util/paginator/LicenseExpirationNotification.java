package com.kurko67.controlflotas.util.paginator;

import com.kurko67.controlflotas.models.dao.INotificacionDao;
import com.kurko67.controlflotas.models.dao.IUsuarioDao;
import com.kurko67.controlflotas.models.entity.Conductor;
import com.kurko67.controlflotas.models.entity.Notificacion;
import com.kurko67.controlflotas.models.entity.Usuario;
import com.kurko67.controlflotas.models.entity.Vehiculo;
import com.kurko67.controlflotas.models.service.IConductorService;
import com.kurko67.controlflotas.models.service.IVehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class LicenseExpirationNotification {

    @Autowired
    private IConductorService conductorService;

    @Autowired
    private INotificacionDao notificacionDao;

    @Autowired
    private IUsuarioDao usuarioDao;

    @Autowired
    private IVehiculoService vehiculoService;

    @Scheduled(cron = "${tarea.programada.cron}")
    public void sendLicenseExpirationNotifications() {

        System.out.println("ejecutando tarea programada sendLicenseExpirationNotifications");

        List<Conductor> conductores = conductorService.findConductoresPorVencerLicencia();

        //Creamos el usuario sistema

        Usuario emisor = usuarioDao.findByUsername("jpablo");

        for (Conductor conductor : conductores) {

            Notificacion notificacion = new Notificacion();

            //definir emisor
            notificacion.setEmisor(emisor);
            // receptor
            notificacion.setReceptor(conductor.getUsuario());
            //demas atributos
            notificacion.setTipo("RECORDATORIO");
            notificacion.setCreated_at(new Date());
            notificacion.setAsunto("Vencimiento licencia");
            String mensaje = "Le recordamos que su licencia de conducir vencerá el día " + conductor.getVencimientoLicencia() + ".";
            notificacion.setMensaje(mensaje);
            notificacionDao.save(notificacion);
        }
    }


    @Scheduled(cron = "${tarea.programada.cron}")
    public void sendVtvExpiration() {

        System.out.println("ejecutando tarea programada sendVtvExpiration");

        List<Vehiculo> vehiculos = vehiculoService.findVehiculoByVtvExpire();

        //Creamos el usuario sistema

        Usuario emisor = usuarioDao.findByUsername("jpablo");

        for (Vehiculo vehiculo : vehiculos) {

            Notificacion notificacion = new Notificacion();

            //definir emisor
            notificacion.setEmisor(emisor);
            // receptor
            notificacion.setReceptor(vehiculo.getConductor().getUsuario());
            //demas atributos
            notificacion.setTipo("RECORDATORIO");
            notificacion.setCreated_at(new Date());
            notificacion.setAsunto("Vencimiento VTV");
            String mensaje = "Le recordamos que la Verificación Técnica Vehicular (VTV) del vehiculo " + vehiculo.getMarca() + ", Modelo "
                    + vehiculo.getAnio() + ", Dominio " + vehiculo.getPatente() + " vencerá el dia: " + vehiculo.getVencimientoVtv();
            notificacion.setMensaje(mensaje);
            notificacionDao.save(notificacion);
        }
    }

    @Scheduled(cron = "${tarea.programada.cron}")
    public void sendRutaExpiration() {

        System.out.println("ejecutando tarea programada sendRutaExpiration");

        List<Vehiculo> vehiculos = vehiculoService.findVehiculoByRutaExpire();

        //Creamos el usuario sistema

        Usuario emisor = usuarioDao.findByUsername("jpablo");

        for (Vehiculo vehiculo : vehiculos) {

            Notificacion notificacion = new Notificacion();

            //definir emisor
            notificacion.setEmisor(emisor);
            // receptor
            notificacion.setReceptor(vehiculo.getConductor().getUsuario());
            //demas atributos
            notificacion.setTipo("RECORDATORIO");
            notificacion.setCreated_at(new Date());
            notificacion.setAsunto("Vencimiento RUTA");
            String mensaje = "Le recordamos que el Registro Único de Transporte Automotor (RUTA) del vehiculo " + vehiculo.getMarca() + ", Modelo "
                    + vehiculo.getAnio() + ", Dominio " + vehiculo.getPatente() + " vencerá el dia: " + vehiculo.getVencimientoVtv();
            notificacion.setMensaje(mensaje);
            notificacionDao.save(notificacion);
        }
    }

    @Scheduled(cron = "${tarea.programada.cron}")
    public void sendSeguroExpiration() {

        System.out.println("ejecutando tarea programada sendSeguroExpiration");

        List<Vehiculo> vehiculos = vehiculoService.findVehiculoBySeguroExpire();

        //Creamos el usuario sistema

        Usuario emisor = usuarioDao.findByUsername("jpablo");

        for (Vehiculo vehiculo : vehiculos) {

            Notificacion notificacion = new Notificacion();

            //definir emisor
            notificacion.setEmisor(emisor);
            // receptor
            notificacion.setReceptor(vehiculo.getConductor().getUsuario());
            //demas atributos
            notificacion.setTipo("RECORDATORIO");
            notificacion.setCreated_at(new Date());
            notificacion.setAsunto("Vencimiento SEGURO");
            String mensaje = "Le recordamos que el Seguro del vehiculo " + vehiculo.getMarca() + ", Modelo "
                    + vehiculo.getAnio() + ", Dominio " + vehiculo.getPatente() + " vencerá el dia: " + vehiculo.getVencimientoVtv();
            notificacion.setMensaje(mensaje);
            notificacionDao.save(notificacion);
        }
    }

}
