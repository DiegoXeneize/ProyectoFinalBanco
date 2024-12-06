package ar.edu.utn.frbb.tup.sistemabancario.model;

import java.time.LocalDate;

public class Prestamo {
    private int id;
    private Cliente cliente;
    private double monto;
    private TipoMoneda moneda;
    private int plazoMeses;
    private double interesAnual = 0.05; // 5% anual
    private double saldoRestante;
    private LocalDate fechaCreacion;

    public Prestamo() {
        this.fechaCreacion = LocalDate.now();
    }

    public Prestamo(Cliente cliente, double monto, TipoMoneda moneda, int plazoMeses) {
        this();
        this.cliente = cliente;
        this.monto = monto;
        this.moneda = moneda;
        this.plazoMeses = plazoMeses;
        this.saldoRestante = monto;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public TipoMoneda getMoneda() {
        return moneda;
    }

    public void setMoneda(TipoMoneda moneda) {
        this.moneda = moneda;
    }

    public int getPlazoMeses() {
        return plazoMeses;
    }

    public void setPlazoMeses(int plazoMeses) {
        this.plazoMeses = plazoMeses;
    }

    public double getInteresAnual() {
        return interesAnual;
    }

    public void setInteresAnual(double interesAnual) {
        this.interesAnual = interesAnual;
    }

    public double getSaldoRestante() {
        return saldoRestante;
    }

    public void setSaldoRestante(double saldoRestante) {
        this.saldoRestante = saldoRestante;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
