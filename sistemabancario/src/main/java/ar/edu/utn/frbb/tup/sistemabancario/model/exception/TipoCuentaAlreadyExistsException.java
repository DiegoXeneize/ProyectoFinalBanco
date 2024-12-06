package ar.edu.utn.frbb.tup.sistemabancario.model.exception;

public class TipoCuentaAlreadyExistsException extends RuntimeException {
    public TipoCuentaAlreadyExistsException(String message) {
        super(message);
    }
}
