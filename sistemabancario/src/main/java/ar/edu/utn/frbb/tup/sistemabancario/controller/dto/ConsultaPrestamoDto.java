package ar.edu.utn.frbb.tup.sistemabancario.controller.dto;

public class ConsultaPrestamoDto {
    private long idPrestamo;
    private double monto;
    private int plazoMeses;
    private int pagosRealizados;
    private double saldoRestante;
    
    public long getIdPrestamo() {
        return idPrestamo;
    }
    public void setIdPrestamo(long idPrestamo) {
        this.idPrestamo = idPrestamo;
    }
    public double getMonto() {
        return monto;
    }
    public void setMonto(double monto) {
        this.monto = monto;
    }
    public int getPlazoMeses() {
        return plazoMeses;
    }
    public void setPlazoMeses(int plazoMeses) {
        this.plazoMeses = plazoMeses;
    }
    public int getPagosRealizados() {
        return pagosRealizados;
    }
    public void setPagosRealizados(int pagosRealizados) {
        this.pagosRealizados = pagosRealizados;
    }
    public double getSaldoRestante() {
        return saldoRestante;
    }
    public void setSaldoRestante(double saldoRestante) {
        this.saldoRestante = saldoRestante;
    }

    
}
