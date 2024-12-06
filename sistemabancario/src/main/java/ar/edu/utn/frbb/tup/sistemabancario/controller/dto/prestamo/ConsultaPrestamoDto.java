package ar.edu.utn.frbb.tup.sistemabancario.controller.dto.prestamo;

public class ConsultaPrestamoDto {
    private long idPrestamo;
    private String moneda;
    private double monto;
    private int plazoMeses;
    private int pagosRealizados;
    private double saldoRestante;

    public ConsultaPrestamoDto() {
    }


    public ConsultaPrestamoDto(long idPrestamo, String moneda,double monto, int plazoMeses, int pagosRealizados, double saldoRestante) {
        this.idPrestamo = idPrestamo;
        this.moneda = moneda;
        this.monto = monto;
        this.plazoMeses = plazoMeses;
        this.pagosRealizados = pagosRealizados;
        this.saldoRestante = saldoRestante;
    }


    public long getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(long idPrestamo) {
        this.idPrestamo = idPrestamo;
    }
    public String getMoneda() {
        return moneda;
    }
    public void setMoneda(String moneda) {
        this.moneda = moneda;
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
