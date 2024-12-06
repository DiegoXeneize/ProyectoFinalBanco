package ar.edu.utn.frbb.tup.sistemabancario.controller.exceptions;

public class NotFormatDateException extends RuntimeException{

    public NotFormatDateException(String message){
        super(message);
    }
}
