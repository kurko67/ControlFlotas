package com.kurko67.controlsalones.controllers;

import com.kurko67.controlsalones.models.entity.Conductor;
import com.kurko67.controlsalones.models.service.ICheckListService;
import com.kurko67.controlsalones.models.service.IConductorService;
import com.kurko67.controlsalones.models.service.IMantenimientoService;
import com.kurko67.controlsalones.models.service.IVehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller()
public class DashBoardController {

    @Autowired
    private IConductorService conductorService;
    @Autowired
    private IVehiculoService vehiculoService;

    @Autowired
    IMantenimientoService mantenimientoService;

    @Autowired
    ICheckListService checkListService;

    @GetMapping("/")
    public String panelAdministrador(Model model, RedirectAttributes flash){

        List<Conductor> conductores = conductorService.findConductoresPorVencerLicencia();
        List<Object[]> vehiculos = vehiculoService.find30daysExpires();
        BigDecimal gastoMensual = mantenimientoService.findGastosMensuales() == null ? BigDecimal.valueOf(0) : mantenimientoService.findGastosMensuales();

        /* contar problematicas */

        Integer contarNeumaticos = checkListService.ContarNeumaticos();
        Integer contarFluidos = checkListService.ContarFluidos();
        Integer contarLuces = checkListService.ContarLuces();
        Integer contarFrenos = checkListService.ContarFrenos();
        Integer contarTrenDelantero = checkListService.ContarTrenDelantero();
        Integer contarSeguridad = checkListService.ContarSeguridad();
        Integer contarCarroceria = checkListService.ContarCarroceria();
        Integer contarDocumentacion = checkListService.ContarDocumentacion();

        int suma;
        suma = contarNeumaticos + contarFluidos + contarLuces + contarFrenos + contarTrenDelantero + contarSeguridad +  contarCarroceria + contarDocumentacion;


        // Obtener la lista de mantenimientos
        List<Object[]> mantenimientos_mes_anterior = mantenimientoService.obtenerMantenimientos();

        // Listas para almacenar resultados
        List<String> dates = new ArrayList<>();
        List<Long> mantenimientoCantidades = new ArrayList<>(); // Usar Long si el conteo es Long

        // Formato para la fecha
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // Procesar los resultados
        for (Object[] mant : mantenimientos_mes_anterior) {
            // Convertir el conteo
            Long cantidad = ((Number) mant[0]).longValue(); // Convertir a Long

            // Convertir la fecha
            Timestamp fecha = (Timestamp) mant[1];
            String fechaFormateada = sdf.format(fecha);

            // Agregar a las listas
            mantenimientoCantidades.add(cantidad);
            dates.add(fechaFormateada);
        }




        model.addAttribute("ordenes", mantenimientoCantidades);
        model.addAttribute("fechas", dates);

        model.addAttribute("conductores", conductores);
        model.addAttribute("vehiculos", vehiculos);
        model.addAttribute("gastos", gastoMensual);
        model.addAttribute("problematicas", suma);

        return "index";

    }

    @GetMapping("/events")
    public String viewCalendar(Model model){

        return "calendar";

    }

}
