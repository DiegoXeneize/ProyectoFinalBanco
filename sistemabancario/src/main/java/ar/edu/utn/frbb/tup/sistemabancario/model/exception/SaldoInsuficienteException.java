package ar.edu.utn.frbb.tup.sistemabancario.model.exception;

public class SaldoInsuficienteException extends RuntimeException{
    public SaldoInsuficienteException(String message){
        super(message);
    }
}
