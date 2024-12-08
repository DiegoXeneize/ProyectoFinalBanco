package ar.edu.utn.frbb.tup.sistemabancario.persistence.entity;

import ar.edu.utn.frbb.tup.sistemabancario.model.Cuota;

public class CuotaEntity {

    private final int cuotaNro;
    private final double monto;

    public CuotaEntity(Cuota cuota) {
        this.cuotaNro = cuota.getCuotaNro();
        this.monto = cuota.getMonto();
    }

    public Cuota toCuota() {
        Cuota cuota = new Cuota();
        cuota.setCuotaNro(this.cuotaNro);
        cuota.setMonto(this.monto);
        return cuota;
    }

    public int getCuotaNro() {
        return cuotaNro;
    }

    public double getMonto() {
        return monto;
    }
}
