package ar.edu.utn.frbb.tup.sistemabancario.controller.dto.prestamo;

import java.util.List;

public class PrestamoResponseDto {
    private String estado;
    private String mensaje;
    private List<PlanPagoDto> planPagos;

    public PrestamoResponseDto() {
    }
    
    public PrestamoResponseDto(String estado, String mensaje) {
        this.estado = estado;
        this.mensaje = mensaje;
    }

    public PrestamoResponseDto(String estado, String mensaje, List<PlanPagoDto> planPagos) {
        this.estado = estado;
        this.mensaje = mensaje;
        this.planPagos = planPagos;
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

    public List<PlanPagoDto> getPlanPagos() {
        return planPagos;
    }

    public void setPlanPagos(List<PlanPagoDto> planPagos) {
        this.planPagos = planPagos;
    }
}

