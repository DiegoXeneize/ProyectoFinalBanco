package ar.edu.utn.frbb.tup.sistemabancario.controller.dto;

import java.util.List;

public class PrestamoResponseDto {
    private String estado; 
    private String mensaje;
    private List<PlanPagoDto> planPagos;
    
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