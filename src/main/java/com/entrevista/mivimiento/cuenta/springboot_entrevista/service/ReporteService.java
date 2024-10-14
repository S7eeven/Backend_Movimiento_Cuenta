package com.entrevista.mivimiento.cuenta.springboot_entrevista.service;

import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.entrevista.mivimiento.cuenta.springboot_entrevista.client.ClienteClient;
import com.entrevista.mivimiento.cuenta.springboot_entrevista.dto.ClienteDTO;
import com.entrevista.mivimiento.cuenta.springboot_entrevista.dto.CuentaDTO;
import com.entrevista.mivimiento.cuenta.springboot_entrevista.dto.EstadoCuentaDTO;
import com.entrevista.mivimiento.cuenta.springboot_entrevista.dto.MovimientoDTO;
import com.entrevista.mivimiento.cuenta.springboot_entrevista.entity.Cuenta;
import com.entrevista.mivimiento.cuenta.springboot_entrevista.entity.Movimiento;
import com.entrevista.mivimiento.cuenta.springboot_entrevista.exception.ResourceNotFoundException;


@Service
public class ReporteService {
    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private MovimientoService movimientoService;

    @Autowired
    private ClienteClient clienteClient;

    public EstadoCuentaDTO generarEstadoCuenta(Long clienteId, LocalDate fechaInicio, LocalDate fechaFin) {
        // Obtener datos del cliente desde Cliente-Service
        ClienteDTO cliente = clienteClient.obtenerClientePorId(clienteId);
        if(cliente == null) {
            throw new ResourceNotFoundException("Cliente no encontrado con id " + clienteId);
        }

        EstadoCuentaDTO estadoCuenta = new EstadoCuentaDTO();
        estadoCuenta.setClienteId(cliente.getClienteid());
        estadoCuenta.setNombreCliente(cliente.getNombre());

        // Obtener cuentas del cliente
        List<Cuenta> cuentas = cuentaService.listarCuentasPorClienteId(clienteId);

        estadoCuenta.setCuentas(
                cuentas.stream().map(cuenta -> {
                    CuentaDTO cuentaDTO = new CuentaDTO();
                    cuentaDTO.setNumeroCuenta(cuenta.getNumeroCuenta());
                    cuentaDTO.setTipoCuenta(cuenta.getTipoCuenta());
                    cuentaDTO.setSaldoInicial(cuenta.getSaldoInicial());

                    List<Movimiento> movimientos = movimientoService.listarMovimientosPorCuentaIdYFecha(cuenta.getId(), fechaInicio, fechaFin);
                    List<MovimientoDTO> movimientosDTO = movimientos.stream()
                            .map(mov -> {
                                MovimientoDTO movDTO = new MovimientoDTO();
                                movDTO.setId(mov.getId());
                                movDTO.setFecha(mov.getFecha());
                                movDTO.setTipoMovimiento(mov.getTipoMovimiento());
                                movDTO.setValor(mov.getValor());
                                movDTO.setSaldoDisponible(mov.getSaldoDisponible());
                                return movDTO;
                            })
                            .collect(Collectors.toList());

                    cuentaDTO.setMovimientos(movimientosDTO);

                    return cuentaDTO;
                }).collect(Collectors.toList())
        );

        return estadoCuenta;
    }
}
