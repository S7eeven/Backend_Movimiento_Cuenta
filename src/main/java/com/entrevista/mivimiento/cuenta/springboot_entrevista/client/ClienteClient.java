package com.entrevista.mivimiento.cuenta.springboot_entrevista.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.entrevista.mivimiento.cuenta.springboot_entrevista.dto.ClienteDTO;;

@FeignClient(name = "cliente-service", url = "${cliente.service.url}")
public interface ClienteClient {
    @GetMapping("/api/clientes/{id}/dto")
    ClienteDTO obtenerClientePorId(@PathVariable("id") Long id);
}
