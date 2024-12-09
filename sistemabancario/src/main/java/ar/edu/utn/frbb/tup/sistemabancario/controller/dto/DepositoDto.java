package ar.edu.utn.frbb.tup.sistemabancario.controller.dto;

public class DepositoDto {
    private long numeroCuenta;
    private double monto;

    public long getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(long numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }
}
