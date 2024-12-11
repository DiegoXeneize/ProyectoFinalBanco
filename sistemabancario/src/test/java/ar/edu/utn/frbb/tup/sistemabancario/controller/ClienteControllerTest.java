package ar.edu.utn.frbb.tup.sistemabancario.controller;

import ar.edu.utn.frbb.tup.sistemabancario.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.sistemabancario.controller.validations.ClienteValidator;
import ar.edu.utn.frbb.tup.sistemabancario.controller.validations.ValidationInput;
import ar.edu.utn.frbb.tup.sistemabancario.model.Cliente;
import ar.edu.utn.frbb.tup.sistemabancario.model.exception.ClienteAlreadyExistsException;
import ar.edu.utn.frbb.tup.sistemabancario.model.exception.ClienteNoExistsException;
import ar.edu.utn.frbb.tup.sistemabancario.model.exception.CuentaNoEncontradaException;
import ar.edu.utn.frbb.tup.sistemabancario.model.exception.MenorEdadException;
import ar.edu.utn.frbb.tup.sistemabancario.service.implementation.ClienteServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteControllerTest {

    @InjectMocks
    private ClienteController clienteController;

    @Mock
    private ClienteServiceImplementation clienteService;

    @Mock
    private ClienteValidator clienteValidator;

    @Mock
    private ValidationInput validationInput;

    private ClienteDto clienteDto;

    @BeforeEach
    public void setUp() {
        clienteDto = new ClienteDto();
        clienteDto.setNombre("Diego");
        clienteDto.setApellido("Bruno");
        clienteDto.setDni(42958843L);
        clienteDto.setFechaNacimiento("2000-09-30");
        clienteDto.setTipoPersona("F");
    }

    @Test
    public void testCrearClienteExitoso() throws ClienteAlreadyExistsException, MenorEdadException {
        // Simula que no hay excepción al crear el cliente
        doNothing().when(validationInput).validarInputCliente(clienteDto);
        doNothing().when(clienteService).darAltaCliente(clienteDto);

        ResponseEntity<Object> response = clienteController.crearCliente(clienteDto, null);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Cliente creado con éxito", response.getBody());
        verify(clienteService, times(1)).darAltaCliente(clienteDto);
    }

    @Test
    public void testCrearClienteYaExistente() throws ClienteAlreadyExistsException, MenorEdadException {
        doThrow(ClienteAlreadyExistsException.class).when(clienteService).darAltaCliente(clienteDto);

        // Verificamos que tire la excepcion correspondiente
        assertThrows(ClienteAlreadyExistsException.class, () -> clienteController.crearCliente(clienteDto, null));
    }

    @Test
    public void testBuscarClientePorDni() throws ClienteNoExistsException, MenorEdadException {
        when(clienteService.buscarClientePorDni(42958843L)).thenReturn(new Cliente(clienteDto));

        Cliente cliente = clienteController.buscarClientePorDni(42958843L, null);

        Cliente clienteExpected = new Cliente(clienteDto);

        assertEquals(clienteExpected.getApellido(), cliente.getApellido());
        assertEquals(clienteExpected.getBanco(), cliente.getBanco());
        assertEquals(clienteExpected.getDni(), cliente.getDni());
        assertEquals(clienteExpected.getEdad(), cliente.getEdad());
        assertEquals(clienteExpected.getFechaNacimiento(), cliente.getFechaNacimiento());
        assertEquals(clienteExpected.getNombre(), cliente.getNombre());
        assertEquals(clienteExpected.getTipoPersona(), cliente.getTipoPersona());
    }   

    @Test
    public void testActualizarClienteExitoso() throws ClienteNoExistsException, MenorEdadException, CuentaNoEncontradaException {
        long dni = 42958842;
        ClienteDto clienteDtoActualizado = new ClienteDto();
        clienteDtoActualizado.setNombre("Diego");
        clienteDtoActualizado.setApellido("Bruno");
        clienteDtoActualizado.setDni(dni);
        clienteDtoActualizado.setFechaNacimiento("2000-09-30");
        clienteDtoActualizado.setTipoPersona("F");

        doNothing().when(clienteService).updateCliente(clienteDtoActualizado, dni);

        ResponseEntity<Object> response = clienteController.updateCliente(dni, clienteDtoActualizado, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cliente modificado con exito", response.getBody());
        verify(clienteService, times(1)).updateCliente(clienteDtoActualizado, dni);
    }




}
