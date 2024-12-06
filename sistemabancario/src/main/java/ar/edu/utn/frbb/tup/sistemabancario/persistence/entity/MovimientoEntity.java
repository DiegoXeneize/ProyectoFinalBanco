package ar.edu.utn.frbb.tup.sistemabancario.persistence.entity;


import ar.edu.utn.frbb.tup.sistemabancario.model.Movimientos;

import java.time.LocalDate;


public class MovimientoEntity extends BaseEntity {

    private LocalDate fechaMovimiento;
    private long numeroCuenta;
    private double monto;


    public MovimientoEntity(Movimientos movimientos) {
        super(movimientos.getIdTransaccion());
        this.fechaMovimiento = movimientos.getFechaMovimiento();
        this.numeroCuenta = movimientos.getCuenta().getNumeroCuenta();
        this.monto = movimientos.getMonto();
    }


    public Movimientos toMovimiento(){
        Movimientos movimientos = new Movimientos();

        movimientos.setIdTransaccion(super.getId());
        movimientos.setFechaMovimiento(this.fechaMovimiento);
        movimientos.setMonto(this.monto);

        return movimientos;

    }


    public LocalDate getFechaMovimiento() {
        return fechaMovimiento;
    }

    public void setFechaMovimiento(LocalDate fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    public long getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(long numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }
}
