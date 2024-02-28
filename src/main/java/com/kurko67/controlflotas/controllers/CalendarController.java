package com.kurko67.controlflotas.controllers;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.kurko67.controlflotas.models.dao.IMantenimientoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;




@RestController
public class CalendarController {


    @Autowired
    IMantenimientoDao er;


    @GetMapping("/api/events")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    Iterable<Object[]> events(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start, @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) throws UnsupportedEncodingException {

        return er.findBetween(start, end);

    }





}
