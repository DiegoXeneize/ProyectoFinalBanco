package ar.edu.utn.frbb.tup.sistemabancario.model.exception;

public class CantidadNegativaException extends  RuntimeException{
    public CantidadNegativaException(String message){
        super(message);

    }
}
