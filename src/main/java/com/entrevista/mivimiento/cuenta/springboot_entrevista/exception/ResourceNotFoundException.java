package com.entrevista.mivimiento.cuenta.springboot_entrevista.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String mensaje) {
        super(mensaje);
    }
}
