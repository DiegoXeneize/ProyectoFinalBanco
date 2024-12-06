package ar.edu.utn.frbb.tup.sistemabancario.controller.dto;

public class ClienteDto extends PersonaDto {

    private String tipoPersona;
    private String banco;


    public String getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(String tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public String getBanco() {
        return "Banco UTN";
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

}
