package com.entrevista.mivimiento.cuenta.springboot_entrevista;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
//import com.entrevista.mivimiento.cuenta.springboot_entrevista.entity.Cuenta;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.entrevista.mivimiento.cuenta.springboot_entrevista.client") // Ajusta el paquete según tu estructura
public class SpringbootEntrevistaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootEntrevistaApplication.class, args);
	}

}
