package ar.edu.utn.frbb.tup.sistemabancario.controller.dto;

public class PagoCuotaResponseDto {
    private long numeroPrestamo;
    private String estado;
    private String mensaje;

    public long getNumeroPrestamo() {
        return numeroPrestamo;
    }

    public void setNumeroPrestamo(long numeroPrestamo) {
        this.numeroPrestamo = numeroPrestamo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}