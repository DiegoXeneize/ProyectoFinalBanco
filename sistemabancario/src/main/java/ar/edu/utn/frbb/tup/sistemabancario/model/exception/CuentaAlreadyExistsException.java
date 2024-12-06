package ar.edu.utn.frbb.tup.sistemabancario.model.exception;

public class CuentaAlreadyExistsException extends  RuntimeException {
    public CuentaAlreadyExistsException(String message){
        super(message);
    }
}