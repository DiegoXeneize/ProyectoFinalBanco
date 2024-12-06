package ar.edu.utn.frbb.tup.sistemabancario.controller.dto.prestamo;

public class PagoCuotaPrestamoDto {
    private long numeroCliente;
    private long numeroPrestamo;
    private String moneda;
    
    public PagoCuotaPrestamoDto(long numeroCliente, long numeroPrestamo, String moneda) {
        this.numeroCliente = numeroCliente;
        this.numeroPrestamo = numeroPrestamo;
        this.moneda = moneda;
    }

    public long getNumeroCliente() {
        return numeroCliente;
    }

    public void setNumeroCliente(long numeroCliente) {
        this.numeroCliente = numeroCliente;
    }

    public long getNumeroPrestamo() {
        return numeroPrestamo;
    }

    public void setNumeroPrestamo(long numeroPrestamo) {
        this.numeroPrestamo = numeroPrestamo;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }
}
