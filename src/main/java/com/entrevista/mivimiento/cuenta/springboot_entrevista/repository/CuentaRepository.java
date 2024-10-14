package com.entrevista.mivimiento.cuenta.springboot_entrevista.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.entrevista.mivimiento.cuenta.springboot_entrevista.entity.Cuenta;

@Repository
public interface CuentaRepository  extends JpaRepository<Cuenta, Long> {
    Optional<Cuenta> findByNumeroCuenta(String numeroCuenta);
    List<Cuenta> findByClienteId(Long clienteId);
}
