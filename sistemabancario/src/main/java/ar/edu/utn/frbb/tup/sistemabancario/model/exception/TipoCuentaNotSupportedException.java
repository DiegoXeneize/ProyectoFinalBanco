package ar.edu.utn.frbb.tup.sistemabancario.model.exception;

public class TipoCuentaNotSupportedException extends RuntimeException {
    public TipoCuentaNotSupportedException(String message) {
        super(message);
    }
}

