package ar.edu.utn.frbb.tup.sistemabancario.model.exception;

public class CuentaNoEncontradaException extends RuntimeException {
    public CuentaNoEncontradaException(String message) {
        super(message);
    }
}
