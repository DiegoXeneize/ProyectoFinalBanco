package ar.edu.utn.frbb.tup.sistemabancario.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;

public class Movimientos {

    private long idTransaccion;
    private LocalDate fechaMovimiento;
    private double monto;
    @JsonIgnore
    private Cuenta cuenta;
    private static int contador = 1;

    public Movimientos(){
        this.idTransaccion = contador++;
        this.fechaMovimiento = LocalDate.now();
    }

    public long getIdTransaccion() {
        return idTransaccion;
    }

    public LocalDate getFechaMovimiento() {
        return fechaMovimiento;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public void setIdTransaccion(long idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public void setFechaMovimiento(LocalDate fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }
}
