package com.entrevista.mivimiento.cuenta.springboot_entrevista.exception;

public class InsufficientBalanceException extends RuntimeException {
      public InsufficientBalanceException(String mensaje) {
        super(mensaje);
    }
}
