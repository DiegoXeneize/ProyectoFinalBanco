package ar.edu.utn.frbb.tup.sistemabancario.persistence.entity;

import ar.edu.utn.frbb.tup.sistemabancario.model.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PrestamoEntity extends BaseEntity {

    private final long numeroCliente;
    private final double monto;
    private final String moneda;
    private final int plazoMeses;
    private final double interes;
    private final double montoCuota;
    private final int pagosRealizados;
    private final double saldoRestante;
    private final LocalDate fechaAltaPrestamo;

    private final List<CuotaEntity> planPagos = new ArrayList<>();

    public PrestamoEntity(Prestamo prestamo) {
        super(prestamo.getId());
        this.numeroCliente = prestamo.getNumeroCliente();
        this.monto = prestamo.getMonto();
        this.moneda = prestamo.getMoneda();
        this.plazoMeses = prestamo.getPlazoMeses();
        this.interes = prestamo.getInteres();
        this.montoCuota = prestamo.getMontoCuota();
        this.pagosRealizados = prestamo.getPagosRealizados();
        this.saldoRestante = prestamo.getSaldoRestante();
        this.fechaAltaPrestamo = prestamo.getFechaAltaPrestamo();

        if (prestamo.getPlanPagos() != null && !prestamo.getPlanPagos().isEmpty()) {
            for (Cuota cuota : prestamo.getPlanPagos()) {
                this.planPagos.add(new CuotaEntity(cuota));
            }
        }
    }

    public Prestamo toPrestamo() {
        Prestamo prestamo = new Prestamo();
        prestamo.setId(super.getId());
        prestamo.setNumeroCliente(this.numeroCliente);
        prestamo.setMonto(this.monto);
        prestamo.setMoneda(this.moneda);
        prestamo.setPlazoMeses(this.plazoMeses);
        prestamo.setInteres(this.interes);
        prestamo.setMontoCuota(this.montoCuota);
        prestamo.setPagosRealizados(this.pagosRealizados);
        prestamo.setSaldoRestante(this.saldoRestante);
        prestamo.setFechaAltaPrestamo(this.fechaAltaPrestamo);

        if (this.planPagos != null && !this.planPagos.isEmpty()) {
            List<Cuota> cuotas = new ArrayList<>();
            for (CuotaEntity cuotaEntity : this.planPagos) {
                cuotas.add(cuotaEntity.toCuota());
            }
            prestamo.setPlanPagos(cuotas);
        }

        return prestamo;
    }

    public long getNumeroCliente() {
        return numeroCliente;
    }

    public double getMonto() {
        return monto;
    }

    public String getMoneda() {
        return moneda;
    }

    public int getPlazoMeses() {
        return plazoMeses;
    }

    public double getInteres() {
        return interes;
    }

    public double getMontoCuota() {
        return montoCuota;
    }

    public int getPagosRealizados() {
        return pagosRealizados;
    }

    public double getSaldoRestante() {
        return saldoRestante;
    }

    public LocalDate getFechaAltaPrestamo() {
        return fechaAltaPrestamo;
    }

    public List<CuotaEntity> getPlanPagos() {
        return planPagos;
    }
}
