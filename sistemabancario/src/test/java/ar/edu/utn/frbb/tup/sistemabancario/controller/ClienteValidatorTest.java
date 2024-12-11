package ar.edu.utn.frbb.tup.sistemabancario.controller;

import ar.edu.utn.frbb.tup.sistemabancario.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.sistemabancario.controller.validations.ClienteValidator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteValidatorTest {

    private final ClienteValidator clienteValidator = new ClienteValidator();

    @Test
    public void testValidarClienteCorrecto() {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setTipoPersona("F");
        clienteDto.setFechaNacimiento("30/09/2000");

        assertDoesNotThrow(() -> clienteValidator.validate(clienteDto));
    }

    @Test
    public void testValidarClienteTipoPersonaNulo() {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setTipoPersona(null);
        clienteDto.setFechaNacimiento("30/09/2000");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> clienteValidator.validate(clienteDto));
        assertEquals("El tipo de persona no puede ser nulo o vacío", exception.getMessage());
    }

    @Test
    public void testValidarClienteTipoPersonaVacio() {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setTipoPersona("");
        clienteDto.setFechaNacimiento("30/09/2000");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> clienteValidator.validate(clienteDto));
        assertEquals("El tipo de persona no puede ser nulo o vacío", exception.getMessage());
    }

    @Test
    public void testValidarClienteTipoPersonaIncorrecto() {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setTipoPersona("X");  // Tipo inválido
        clienteDto.setFechaNacimiento("30/09/2000");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> clienteValidator.validate(clienteDto));
        assertEquals("El tipo de persona debe ser 'F' o 'J'", exception.getMessage());
    }

    @Test
    public void testValidarClienteFechaNacimientoNula() {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setTipoPersona("F");
        clienteDto.setFechaNacimiento(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> clienteValidator.validate(clienteDto));
        assertEquals("La fecha de nacimiento no puede ser nula o vacía", exception.getMessage());
    }

    @Test
    public void testValidarClienteFechaNacimientoVacia() {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setTipoPersona("F");
        clienteDto.setFechaNacimiento("");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> clienteValidator.validate(clienteDto));
        assertEquals("La fecha de nacimiento no puede ser nula o vacía", exception.getMessage());
    }

    @Test
    public void testValidarClienteFechaNacimientoFormatoIncorrecto() {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setTipoPersona("F");
        clienteDto.setFechaNacimiento("2000/09/30");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> clienteValidator.validate(clienteDto));
        assertEquals("La fecha de nacimiento no tiene el formato correcto (dd/MM/yyyy)", exception.getMessage());
    }

    @Test
    public void testValidarClienteFechaNacimientoFormatoCorrecto() {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setTipoPersona("F");
        clienteDto.setFechaNacimiento("30/09/2000");

        assertDoesNotThrow(() -> clienteValidator.validate(clienteDto));
    }
}
