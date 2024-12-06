package ar.edu.utn.frbb.tup.sistemabancario.model.exception;

public class ClienteNoCuentaTipoMonedaException extends RuntimeException{
    public ClienteNoCuentaTipoMonedaException(String message){
        super(message);
    }
}
