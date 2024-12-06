package ar.edu.utn.frbb.tup.sistemabancario.model.exception;


public class ClienteNoExistsException extends RuntimeException{
    public ClienteNoExistsException(String message){
        super(message);
    }
}
