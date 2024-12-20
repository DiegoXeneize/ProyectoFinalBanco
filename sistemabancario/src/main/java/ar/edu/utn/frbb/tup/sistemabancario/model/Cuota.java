package ar.edu.utn.frbb.tup.sistemabancario.model;

public class Cuota {

    private int cuotaNro; 
    private double monto;

    public Cuota() {
    }

    public Cuota(int cuotaNro, double monto) {
        this.cuotaNro = cuotaNro;
        this.monto = monto;
    }

    public int getCuotaNro() {
        return cuotaNro;
    }

    public void setCuotaNro(int cuotaNro) {
        this.cuotaNro = cuotaNro;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    @Override
    public String toString() {
        return "Cuota{" +
                "cuotaNro=" + cuotaNro +
                ", monto=" + monto +
                '}';
    }
}
