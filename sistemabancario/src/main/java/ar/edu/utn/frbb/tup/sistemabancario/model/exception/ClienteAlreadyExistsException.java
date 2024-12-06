package ar.edu.utn.frbb.tup.sistemabancario.model.exception;

public class ClienteAlreadyExistsException extends RuntimeException{
    public ClienteAlreadyExistsException(String message){
        super(message);

    }
}