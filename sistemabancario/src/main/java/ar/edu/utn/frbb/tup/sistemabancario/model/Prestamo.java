package ar.edu.utn.frbb.tup.sistemabancario.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Prestamo {

    private long id;
    private long numeroCliente;
    private double monto;
    private String moneda;
    private int plazoMeses;
    private double interes;
    private double montoCuota;
    private int pagosRealizados;
    private double saldoRestante;
    private LocalDate fechaAltaPrestamo;
    private List<Cuota> planPagos;

    // Constructor vacío
    public Prestamo() {
        this.planPagos = new ArrayList<>();
    }

    // Getters y Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getNumeroCliente() {
        return numeroCliente;
    }

    public void setNumeroCliente(long numeroCliente) {
        this.numeroCliente = numeroCliente;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public int getPlazoMeses() {
        return plazoMeses;
    }

    public void setPlazoMeses(int plazoMeses) {
        this.plazoMeses = plazoMeses;
    }

    public double getInteres() {
        return interes;
    }

    public void setInteres(double interes) {
        this.interes = interes;
    }

    public double getMontoCuota() {
        return montoCuota;
    }

    public void setMontoCuota(double montoCuota) {
        this.montoCuota = montoCuota;
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

    public LocalDate getFechaAltaPrestamo() {
        return fechaAltaPrestamo;
    }

    public void setFechaAltaPrestamo(LocalDate fechaAltaPrestamo) {
        this.fechaAltaPrestamo = fechaAltaPrestamo;
    }

    public List<Cuota> getPlanPagos() {
        return planPagos;
    }

    public void setPlanPagos(List<Cuota> planPagos) {
        this.planPagos = planPagos;
    }
}
