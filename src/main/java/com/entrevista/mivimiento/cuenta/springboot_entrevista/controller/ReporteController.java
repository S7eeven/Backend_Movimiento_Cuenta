package com.entrevista.mivimiento.cuenta.springboot_entrevista.controller;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.entrevista.mivimiento.cuenta.springboot_entrevista.dto.EstadoCuentaDTO;
import com.entrevista.mivimiento.cuenta.springboot_entrevista.service.ReporteService;

@RestController
@RequestMapping("/reportes")
public class ReporteController {
    @Autowired
    private ReporteService reporteService;

    @GetMapping
    public ResponseEntity<EstadoCuentaDTO> generarReporteEstadoCuenta(
            @RequestParam Long clienteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        EstadoCuentaDTO estadoCuenta = reporteService.generarEstadoCuenta(clienteId, fechaInicio, fechaFin);
        return ResponseEntity.ok(estadoCuenta);
    }
}
