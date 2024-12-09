package ar.edu.utn.frbb.tup.sistemabancario.controller.validations;


import ar.edu.utn.frbb.tup.sistemabancario.controller.dto.ClienteDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class ClienteValidator {

    public void validate(ClienteDto clienteDto){

        if(clienteDto == null){
            throw new IllegalArgumentException("El cliente no puede ser nulo");
        }

        String tipoPersona = clienteDto.getTipoPersona();
        if (tipoPersona == null || tipoPersona.isEmpty()) {
            throw new IllegalArgumentException("El tipo de persona no puede ser nulo o vacío");
        }

        tipoPersona = tipoPersona.toUpperCase();
        clienteDto.setTipoPersona(tipoPersona);

        if (!"F".equals(tipoPersona) && !"J".equals(tipoPersona)) {
            throw new IllegalArgumentException("El tipo de persona debe ser 'F' o 'J'");
        }
        
        String fechaNacimiento = clienteDto.getFechaNacimiento();
        if (fechaNacimiento == null || fechaNacimiento.isEmpty()) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser nula o vacía");
        }
        
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try{
            LocalDate fechaNacimientoLocalDate = LocalDate.parse(fechaNacimiento, formato);
        }catch (DateTimeParseException e) {
            throw new IllegalArgumentException("La fecha de nacimiento no tiene el formato correcto (dd/MM/yyyy)");
        }



    }
}
