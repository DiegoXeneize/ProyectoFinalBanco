package ar.edu.utn.frbb.tup.sistemabancario.controller.dto;

import java.util.List;

public class PrestamosClienteResponseDto {
    private long numeroCliente;
    private List<ConsultaPrestamoDto> prestamos;
    
    public long getNumeroCliente() {
        return numeroCliente;
    }
    public void setNumeroCliente(long numeroCliente) {
        this.numeroCliente = numeroCliente;
    }
    public List<ConsultaPrestamoDto> getPrestamos() {
        return prestamos;
    }
    public void setPrestamos(List<ConsultaPrestamoDto> prestamos) {
        this.prestamos = prestamos;
    }

    
}
