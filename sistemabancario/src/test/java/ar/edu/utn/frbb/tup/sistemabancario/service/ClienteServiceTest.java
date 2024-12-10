package ar.edu.utn.frbb.tup.sistemabancario.service;

import ar.edu.utn.frbb.tup.sistemabancario.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.sistemabancario.model.Cliente;
import ar.edu.utn.frbb.tup.sistemabancario.model.Cuenta;
import ar.edu.utn.frbb.tup.sistemabancario.model.exception.ClienteAlreadyExistsException;
import ar.edu.utn.frbb.tup.sistemabancario.model.exception.ClienteNoExistsException;
import ar.edu.utn.frbb.tup.sistemabancario.model.exception.CuentaNoEncontradaException;
import ar.edu.utn.frbb.tup.sistemabancario.model.exception.MenorEdadException;
import ar.edu.utn.frbb.tup.sistemabancario.model.exception.TipoCuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.sistemabancario.persistence.implementation.ImplementsClienteDao;
import ar.edu.utn.frbb.tup.sistemabancario.service.implementation.ClienteServiceImplementation;
import ar.edu.utn.frbb.tup.sistemabancario.service.implementation.CuentaServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock
    private ImplementsClienteDao clienteDao;

    @Mock
    private CuentaServiceImplementation cuentaService;

    @InjectMocks
    private ClienteServiceImplementation clienteService;

    private ClienteDto clienteDtoMock;

    @BeforeEach
    public void setUp() {
        clienteDtoMock = new ClienteDto();
        clienteDtoMock.setDni(42958843);
        clienteDtoMock.setFechaNacimiento("2000-09-30");
        clienteDtoMock.setNombre("Diego");
        clienteDtoMock.setApellido("Bruno");
        clienteDtoMock.setTipoPersona("F");
    }

    @Test
    public void testDarAltaClienteSuccess() throws ClienteAlreadyExistsException, MenorEdadException {

        when(clienteDao.find(clienteDtoMock.getDni(), false)).thenReturn(null);

        clienteService.darAltaCliente(clienteDtoMock);

        // Aca verificamos que el metodo haya sido llamado con un cliente y capturamos ese objeto
        ArgumentCaptor<Cliente> captor = ArgumentCaptor.forClass(Cliente.class);
        verify(clienteDao, times(1)).save(captor.capture());
        Cliente clienteGuardado = captor.getValue();

        assertEquals(clienteDtoMock.getDni(), clienteGuardado.getDni());
        assertEquals(clienteDtoMock.getNombre(), clienteGuardado.getNombre());
        assertEquals(clienteDtoMock.getApellido(), clienteGuardado.getApellido());

    }

    @Test
    public void testClienteClienteAlreadyExists() {

        // Aca hacemos que ya existe un cliente con ese dni
        when(clienteDao.find(clienteDtoMock.getDni(), false)).thenReturn(new Cliente());

        // Aca nos aseguramos que tire la excepcion correspondiente de que el cliente ya existe
        assertThrows(ClienteAlreadyExistsException.class, () -> clienteService.darAltaCliente(clienteDtoMock));
    }

    @Test
    public void testMenorEdad(){

        //Aca modificamos la edad del cliente para que sea menor
        clienteDtoMock.setFechaNacimiento("2008-09-30");

        //Verificamos que tire la excepcion correspondiente de que el cliente es menor de edad
        assertThrows(MenorEdadException.class, () -> clienteService.darAltaCliente(clienteDtoMock));
    }

    @Test
    public void testBuscarClientePorDni(){

        Cliente clienteMock = new Cliente(clienteDtoMock);

        //Aca simulamos la buqueda de un cliente que si existe
        when(clienteDao.find(clienteMock.getDni(),true)).thenReturn(clienteMock);

        Cliente cliente = clienteService.buscarClientePorDni(clienteMock.getDni());

        assertEquals(clienteMock.getDni(), cliente.getDni());
    }

    @Test
    public void testBuscarClientePorDniNoEncontrado(){

        //Aca simulamos que el cliente no existe retornando un null
        when(clienteDao.find(clienteDtoMock.getDni(),true)).thenReturn(null);

        //Aca verificamos que tira la excepcion que es correcta
        assertThrows(ClienteNoExistsException.class, () -> clienteService.buscarClientePorDni(clienteDtoMock.getDni()));
    }

    @Test
    public void testAgregarCuentasAlClienteSuccess(){
        Cliente clienteMock = new Cliente(clienteDtoMock);
        Cuenta cuentaMock = new Cuenta();

        when(clienteDao.find(clienteMock.getDni(),true)).thenReturn(clienteMock);
        //Aca nos aseguramos de que no tiene cuentas duplicadas
        when(cuentaService.tieneCuentaDeTipoMoneda(clienteMock.getDni(), cuentaMock.getTipoMoneda(), cuentaMock.getTipoCuenta())).thenReturn(false);

        clienteService.agregarCuentasAlCliente(cuentaMock, clienteMock.getDni());

        //Aca verificamos que el cliente se guardo
        verify(clienteDao, times(1)).save(clienteMock);
    }

    @Test
    public void testAgregarCuentasAlClienteTipoCuentaExiste() throws ClienteNoExistsException, TipoCuentaAlreadyExistsException {
        Cliente clienteMock = new Cliente(clienteDtoMock);
        Cuenta cuentaMock = new Cuenta();

        // Aca simulamos que el cliente existe y tiene una cuenta de ese tipo
        when(clienteDao.find(clienteMock.getDni(), true)).thenReturn(clienteMock);
        when(cuentaService.tieneCuentaDeTipoMoneda(clienteMock.getDni(), cuentaMock.getTipoMoneda(), cuentaMock.getTipoCuenta())).thenReturn(true);

        // Verificamos que tira la excepcion que queremos
        assertThrows(TipoCuentaAlreadyExistsException.class, () -> clienteService.agregarCuentasAlCliente(cuentaMock, clienteMock.getDni()));
        }
        
    }

