package ar.edu.utn.frbb.tup.sistemabancario.controller;

import ar.edu.utn.frbb.tup.sistemabancario.controller.validations.ValidationInput;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValidationInputTest {

    private final ValidationInput validationInput = new ValidationInput();

    // Test para la validación de fecha de nacimiento
    @Test
    public void testValidarFechaNacimientoCorrecta() {
        String fechaNacimientoValida = "2000-09-30"; // Fecha válida
        assertDoesNotThrow(() -> validationInput.validarFechaNacimiento(fechaNacimientoValida));
    }

    @Test
    public void testValidarFechaNacimientoFutura() {
        String fechaNacimientoFutura = "2025-01-012"; // Fecha en el futuro
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
                () -> validationInput.validarFechaNacimiento(fechaNacimientoFutura));
        assertEquals("Formato de fecha de nacimiento inválido. Debe ser yyyy-MM-dd", exception.getMessage());
    }

    @Test
    public void testValidarFechaNacimientoFormatoInvalido() {
        String fechaNacimientoInvalida = "30-09-2000";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
                () -> validationInput.validarFechaNacimiento(fechaNacimientoInvalida));
        assertEquals("Formato de fecha de nacimiento inválido. Debe ser yyyy-MM-dd", exception.getMessage());
    }

    @Test
    public void testValidarDniValido() {
        long dniValido = 12345678L;
        assertDoesNotThrow(() -> validationInput.validarDni(dniValido));
    }

    @Test
    public void testValidarDniInvalidoLargo() {
        long dniInvalido = 123456L; 
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
                () -> validationInput.validarDni(dniInvalido));
        assertEquals("El dni debe contener 7 u 8 caracteres", exception.getMessage());
    }

    @Test
    public void testValidarStringCorrecto() {
        String textoValido = "Diego";
        assertDoesNotThrow(() -> validationInput.validarString(textoValido, "nombre", 3));
    }

    @Test
    public void testValidarStringConMenosDeTresCaracteres() {
        String textoInvalido = "Jo";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
                () -> validationInput.validarString(textoInvalido, "nombre", 3));
        assertEquals("El campo nombre no puede ser nulo y debe contener al menos 3 caracteres", exception.getMessage());
    }

    @Test
    public void testValidarStringConNumeros() {
        String textoInvalido = "Diego123";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
                () -> validationInput.validarString(textoInvalido, "nombre", 3));
        assertEquals("El campo nombre  no puede contener caracteres numericos", exception.getMessage());
    }

    @Test
    public void testValidarNumeroCuentaIncorrecto() {
        long numeroCuentaInvalido = 1234567890L;
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
                () -> validationInput.validarNumeroCuenta(numeroCuentaInvalido));
        assertEquals("El número de cuenta debe contar con 20 caracteres", exception.getMessage());
    }

}
