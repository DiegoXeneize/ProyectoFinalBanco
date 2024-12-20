package ar.edu.utn.frbb.tup.sistemabancario.model;

import ar.edu.utn.frbb.tup.sistemabancario.controller.dto.ClienteDto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Cliente extends Persona{

    private LocalDate fechaAltaCliente;
    private String banco;
    private TipoPersona tipoPersona;
    private Set<Cuenta> cuentasDelCliente;

    public Cliente(){
        this.fechaAltaCliente = LocalDate.now();
        cuentasDelCliente = new HashSet<>();
        this.banco = "Banco UTN";
    }

    public Cliente(ClienteDto clienteDto){
        this();
        this.setDni(clienteDto.getDni());
        this.setNombre(clienteDto.getNombre());
        this.setApellido(clienteDto.getApellido());
        this.setFechaNacimiento(LocalDate.parse(clienteDto.getFechaNacimiento()));
        this.setTipoPersona(TipoPersona.fromString(clienteDto.getTipoPersona()));
        this.setBanco(clienteDto.getBanco());

    }

    public LocalDate getFechaAltaCliente() {
        return fechaAltaCliente;
    }

    public void setFechaAltaCliente(LocalDate fechaAltaCliente) {
        this.fechaAltaCliente = fechaAltaCliente;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public TipoPersona getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(TipoPersona tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public Set<Cuenta> getCuentasDelCliente() {
        return cuentasDelCliente;
    }

    public void setCuentasDelCliente(Cuenta cuenta){
        this.cuentasDelCliente.add(cuenta);
    }

}
