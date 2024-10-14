package com.entrevista.mivimiento.cuenta.springboot_entrevista.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.entrevista.mivimiento.cuenta.springboot_entrevista.entity.Movimiento;
import java.util.List;
import java.time.LocalDate;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    List<Movimiento> findByCuentaId(Long cuentaId);
    List<Movimiento> findByCuentaIdAndFechaBetween(Long cuentaId, LocalDate fechaInicio, LocalDate fechaFin);
}
