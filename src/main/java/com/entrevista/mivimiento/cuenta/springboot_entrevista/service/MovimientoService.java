package com.entrevista.mivimiento.cuenta.springboot_entrevista.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.entrevista.mivimiento.cuenta.springboot_entrevista.entity.Cuenta;
import com.entrevista.mivimiento.cuenta.springboot_entrevista.entity.Movimiento;
import com.entrevista.mivimiento.cuenta.springboot_entrevista.exception.InsufficientBalanceException;
import com.entrevista.mivimiento.cuenta.springboot_entrevista.exception.ResourceNotFoundException;
import com.entrevista.mivimiento.cuenta.springboot_entrevista.repository.CuentaRepository;
import com.entrevista.mivimiento.cuenta.springboot_entrevista.repository.MovimientoRepository;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class MovimientoService {
    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Transactional
    public Movimiento registrarMovimiento(String numeroCuenta, String tipoMovimiento, BigDecimal valor) {
        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con número " + numeroCuenta));

        BigDecimal nuevoSaldo;

        if ("DEPOSITO".equalsIgnoreCase(tipoMovimiento)) {
            nuevoSaldo = cuenta.getSaldoInicial().subtract(valor);
            if (nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
                throw new InsufficientBalanceException("Saldo no disponible");
            }
        } else if ("RETIRO".equalsIgnoreCase(tipoMovimiento)) {
            nuevoSaldo = cuenta.getSaldoInicial().add(valor);
        } else {
            throw new IllegalArgumentException("Tipo de movimiento inválido");
        }

        cuenta.setSaldoInicial(nuevoSaldo);
        cuentaRepository.save(cuenta);

        Movimiento movimiento = new Movimiento();
        movimiento.setFecha(LocalDate.now());
        movimiento.setTipoMovimiento(tipoMovimiento);
        movimiento.setValor(valor);
        movimiento.setSaldoDisponible(nuevoSaldo);
        movimiento.setCuentaId(cuenta.getId());

        return movimientoRepository.save(movimiento);
    }

    public List<Movimiento> listarMovimientosPorCuenta(String numeroCuenta) {
        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con número " + numeroCuenta));
        return movimientoRepository.findByCuentaId(cuenta.getId());
    }

    public List<Movimiento> listarMovimientosPorCuentaIdYFecha(Long cuentaId, LocalDate fechaInicio, LocalDate fechaFin) {
        return movimientoRepository.findByCuentaIdAndFechaBetween(cuentaId, fechaInicio, fechaFin);
    }
}
