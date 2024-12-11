package ar.edu.utn.frbb.tup.sistemabancario.controller.validations;

import ar.edu.utn.frbb.tup.sistemabancario.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.sistemabancario.controller.exceptions.NotFormatDateException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class ValidationInput {

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void validarInputCliente(ClienteDto clienteDto){

        validarDni(clienteDto.getDni());

        validarFechaNacimiento(clienteDto.getFechaNacimiento());

        validarString(clienteDto.getNombre(), "nombre", 3);
        validarString(clienteDto.getApellido(), "apellido", 3);
    }

    public void validarFechaNacimiento(String fechaNacimientoStr){
        try{
            LocalDate fechaNacimiento = LocalDate.parse(fechaNacimientoStr, DATE_FORMATTER);
            LocalDate fechaActual = LocalDate.now();

            if (fechaNacimiento.isAfter(fechaActual)) {
                throw new NotFormatDateException("La fecha de nacimiento no puede estar en el futuro");
            }


        }  catch (DateTimeParseException | NotFormatDateException e) {
            throw new IllegalArgumentException("Formato de fecha de nacimiento inválido. Debe ser yyyy-MM-dd");
        }
    }

    public void validarString(String texto, String campo, int longMinima){

        if(texto == null || texto.length() <= longMinima){
            throw new IllegalArgumentException("El campo " + campo + " no puede ser nulo y debe contener al menos " + longMinima + " caracteres");
        }

        if(!texto.matches("[a-zA-Z]+")){
            throw new IllegalArgumentException("El campo " + campo + "  no puede contener caracteres numericos");
        }

    }

    public void validarDni(long dni){

        String dniStr = String.valueOf(dni);

        if(dniStr.length() != 7 && dniStr.length() != 8){
            throw new IllegalArgumentException("El dni debe contener 7 u 8 caracteres");
        }

        if (!dniStr.matches("\\d{7,8}")) {
            throw new IllegalArgumentException("El número de dni debe contener solo caracteres numéricos");
        }
    }

    public void validarNumeroCuenta(long numeroCuenta){

        String nroCuentaStr = String.valueOf(numeroCuenta);

        if(nroCuentaStr.length() != 20){
            throw new IllegalArgumentException("El número de cuenta debe contar con 20 caracteres");

        }

        if (!nroCuentaStr.matches("\\d{20}")) {
            throw new IllegalArgumentException("El número de cuenta debe contener solo caracteres numéricos");
        }

    }

}
