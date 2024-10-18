package com.entrevista.mivimiento.cuenta.springboot_entrevista.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.entrevista.mivimiento.cuenta.springboot_entrevista.entity.Cuenta;
import com.entrevista.mivimiento.cuenta.springboot_entrevista.exception.ResourceNotFoundException;
import com.entrevista.mivimiento.cuenta.springboot_entrevista.repository.CuentaRepository;
import java.util.List;

@Service
public class CuentaService {
    @Autowired
    private CuentaRepository cuentaRepository;

    public Cuenta crearCuenta(Cuenta cuenta) {
        //Verificar si el cliente existe llamando al Cliente-Service
        return cuentaRepository.save(cuenta);
    }

    public List<Cuenta> listarCuentas() {
        return cuentaRepository.findAll();
    }

    public Cuenta obtenerCuentaPorId(Long id) {
        return cuentaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con id " + id));
    }

    public Cuenta actualizarCuenta(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    public void eliminarCuenta(Long id) {
        cuentaRepository.deleteById(id);
    }

    public Cuenta obtenerCuentaPorNumeroCuenta(String numeroCuenta) {
        return cuentaRepository.findByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con número " + numeroCuenta));
    }

    public List<Cuenta> listarCuentasPorClienteId(Long clienteId) {
        return cuentaRepository.findByClienteId(clienteId);
    }
}
