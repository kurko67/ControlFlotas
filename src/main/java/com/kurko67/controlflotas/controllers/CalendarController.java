package com.kurko67.controlflotas.controllers;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.kurko67.controlflotas.models.dao.IMantenimientoDao;
import com.kurko67.controlflotas.models.entity.Mantenimiento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
public class CalendarController {


    @Autowired
    IMantenimientoDao er;


    @RequestMapping(value = "/api/eventss", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    Iterable<Object[]> events(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start, @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) throws UnsupportedEncodingException {

        return er.findBetween(start, end);

    }

    @RequestMapping(value = "/api/events", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    List<Mantenimiento> eventss(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start, @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) throws UnsupportedEncodingException {
        List<Mantenimiento> eventResponses = new ArrayList<>();

        Iterable<Object[]> result = er.findBetween(start, end);

        for (Object[] row : result) {
            Mantenimiento eventResponse = new Mantenimiento();
            eventResponse.setIdMantenimiento((Long) row[0]);
            eventResponse.setText((String) row[1]);
            eventResponse.setStart((LocalDateTime) row[2]);
            eventResponse.setEnd((LocalDateTime) row[3]);
            //eventResponse.setColor((String) row[4]); // Ajusta esto según tu lógica

            eventResponses.add(eventResponse);
        }

        return eventResponses;
    }


}
