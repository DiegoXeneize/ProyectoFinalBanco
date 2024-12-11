package ar.edu.utn.frbb.tup.sistemabancario.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ar.edu.utn.frbb.tup.sistemabancario.controller.dto.CuentaDto;
import ar.edu.utn.frbb.tup.sistemabancario.model.Cliente;
import ar.edu.utn.frbb.tup.sistemabancario.model.Cuenta;
import ar.edu.utn.frbb.tup.sistemabancario.model.TipoCuenta;
import ar.edu.utn.frbb.tup.sistemabancario.model.TipoMoneda;
import ar.edu.utn.frbb.tup.sistemabancario.model.exception.ClienteNoExistsException;
import ar.edu.utn.frbb.tup.sistemabancario.model.exception.CuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.sistemabancario.model.exception.CuentaNoEncontradaException;
import ar.edu.utn.frbb.tup.sistemabancario.model.exception.SaldoInsuficienteException;
import ar.edu.utn.frbb.tup.sistemabancario.model.exception.TipoCuentaNotSupportedException;
import ar.edu.utn.frbb.tup.sistemabancario.persistence.implementation.ImplementsCuentaDao;
import ar.edu.utn.frbb.tup.sistemabancario.service.implementation.ClienteServiceImplementation;
import ar.edu.utn.frbb.tup.sistemabancario.service.implementation.CuentaServiceImplementation;

public class CuentaServiceTest {

    @Mock
    private ImplementsCuentaDao cuentaDao;

    @Mock
    private ClienteServiceImplementation clienteService;

    @InjectMocks
    private CuentaServiceImplementation cuentaService;

    private Cliente clienteMock;
    private CuentaDto cuentaDtoMock;
    private Cuenta cuentaMock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        clienteMock = new Cliente();
        clienteMock.setDni(42958843L);
        clienteMock.setNombre("Diego");
        clienteMock.setApellido("Bruno");

        cuentaDtoMock = new CuentaDto();
        cuentaDtoMock.setTipoMoneda("ARS");
        cuentaDtoMock.setTipoCuenta("CA");
        cuentaDtoMock.setDniTitular(clienteMock.getDni());

        cuentaMock = new Cuenta();
        cuentaMock.setNumeroCuenta(1L);
        cuentaMock.setSaldo(0.0);
        cuentaMock.setTipoMoneda(TipoMoneda.ARS);
        cuentaMock.setTipoCuenta(TipoCuenta.CAJA_AHORRO);
    }

    @Test
    public void testDarAltaCuentaSuccess() throws CuentaAlreadyExistsException, TipoCuentaNotSupportedException, ClienteNoExistsException {
        when(clienteService.buscarClientePorDni(clienteMock.getDni())).thenReturn(clienteMock);

        when(cuentaDao.find(cuentaMock.getNumeroCuenta(), false)).thenReturn(null);

        cuentaService.darAltaCuenta(cuentaDtoMock, clienteMock.getDni());

        // Verificamos que se guarda la cuenta
        verify(cuentaDao, times(1)).save(any(Cuenta.class));
    }

    @Test
    public void testDarAltaCuentaTipoCuentaNotSupported() {
        cuentaDtoMock.setTipoMoneda("USD");
        cuentaDtoMock.setTipoCuenta("CC"); // Tipo cuenta no soportado

        when(clienteService.buscarClientePorDni(clienteMock.getDni())).thenReturn(clienteMock);

        // Verificamos que se tire la excepcion correspondiente
        assertThrows(TipoCuentaNotSupportedException.class, () -> cuentaService.darAltaCuenta(cuentaDtoMock, clienteMock.getDni()));
    }

    @Test
    public void testListCuentasByClienteSuccess() throws CuentaNoEncontradaException {
        Cliente cliente = new Cliente();
        cliente.setDni(42958843L);
        
        Cuenta cuenta1 = new Cuenta();
        cuenta1.setNumeroCuenta(1L);
        cuenta1.setSaldo(1000);
        cuenta1.setTipoMoneda(TipoMoneda.ARS);
        cuenta1.setTipoCuenta(TipoCuenta.CAJA_AHORRO);
        
        Cuenta cuenta2 = new Cuenta();
        cuenta2.setNumeroCuenta(2L);
        cuenta2.setSaldo(2000);
        cuenta2.setTipoMoneda(TipoMoneda.ARS);
        cuenta2.setTipoCuenta(TipoCuenta.CUENTA_CORRIENTE);
        
        when(cuentaDao.cuentasDelCliente(cliente.getDni())).thenReturn(Arrays.asList(cuenta1, cuenta2));

        List<Cuenta> cuentas = cuentaService.listCuentasByCliente(cliente.getDni());

        //Verificamos que tenemos las cuentas y que tengamos 2 en cuenta size
        assertNotNull(cuentas);
        assertEquals(2, cuentas.size()); 
    }

    @Test
    public void testListCuentasByClienteCuentaNoEncontrada() throws CuentaNoEncontradaException {
        Cliente cliente = new Cliente();
        cliente.setDni(42958843L);

        when(cuentaDao.cuentasDelCliente(cliente.getDni())).thenReturn(new ArrayList<>());

        // Verificamos que se lanza la excepción correspondiente
        assertThrows(CuentaNoEncontradaException.class, () -> cuentaService.listCuentasByCliente(cliente.getDni()));
    }

    @Test
    public void testDepositarSuccess() throws CuentaNoEncontradaException, IllegalArgumentException {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(1L);  // Número de cuenta correcto
        cuenta.setSaldo(1000.0);
        cuenta.setTipoMoneda(TipoMoneda.ARS);
        cuenta.setTipoCuenta(TipoCuenta.CAJA_AHORRO);
        
        when(cuentaDao.find(cuenta.getNumeroCuenta(), true)).thenReturn(cuenta); 

        double saldoNuevo = cuentaService.depositar(cuenta.getNumeroCuenta(), 500.0);

        // Verificar que el saldo se haya actualizado correctamente
        assertEquals(1500.0, saldoNuevo, 0.01); 
    }

    @Test
    public void testDepositarIllegalArgument() throws CuentaNoEncontradaException {
        assertThrows(IllegalArgumentException.class, () -> cuentaService.depositar(1L, -500.0));
    }

    @Test
    public void testActualizarSaldoSuccess() throws CuentaNoEncontradaException {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(1L);
        cuenta.setSaldo(1000.0);
        cuenta.setTipoMoneda(TipoMoneda.ARS);
        cuenta.setTipoCuenta(TipoCuenta.CAJA_AHORRO);
        
        when(cuentaDao.find(1L, false)).thenReturn(cuenta);

        cuentaService.actualizarSaldo(1L, 2000.0);

        verify(cuentaDao, times(1)).updateSaldo(1L, 2000.0);
    }

    @Test
    public void testActualizarSaldoCuentaNoEncontrada() throws CuentaNoEncontradaException {
        when(cuentaDao.find(1L, false)).thenReturn(null);

        // Verificamos que se tira la excepción correspondiente
        assertThrows(CuentaNoEncontradaException.class, () -> cuentaService.actualizarSaldo(1L, 2000.0));
    }

    @Test
    public void testBuscarCuentaPorNumeroSuccess() throws CuentaNoEncontradaException {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(1L);
        
        when(cuentaDao.find(1L, true)).thenReturn(cuenta);

        Cuenta cuentaEncontrada = cuentaService.buscarCuentaPorNumero(1L);

        assertNotNull(cuentaEncontrada);
        assertEquals(1L, cuentaEncontrada.getNumeroCuenta());
    }

    @Test
    public void testBuscarCuentaPorNumeroCuentaNoEncontrada() throws CuentaNoEncontradaException {
        when(cuentaDao.find(1L, true)).thenReturn(null);

        // Verificamos que se tira la excepcion correspondiente
        assertThrows(CuentaNoEncontradaException.class, () -> cuentaService.buscarCuentaPorNumero(1L));
    }

    @Test
    public void testTieneSaldoDisponibleSuccess() {
        Cuenta cuenta = new Cuenta();
        cuenta.setSaldo(2000.0);

        assertTrue(cuentaService.tieneSaldoDisponible(cuenta, 1000.0));

        // Verificamos que no tiene saldo suficiente
        assertFalse(cuentaService.tieneSaldoDisponible(cuenta, 3000.0));
    }

    @Test
    public void testActualizarTitularCuenta() throws CuentaNoEncontradaException {
        Cliente clienteActualizado = new Cliente();
        clienteActualizado.setDni(12345678L);
        clienteActualizado.setNombre("Titular2");

        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(1L);
        cuenta.setTitular(new Cliente());

        when(cuentaDao.cuentasDelCliente(clienteActualizado.getDni())).thenReturn(Arrays.asList(cuenta));

        cuentaService.actualizarTitularCuenta(clienteActualizado, 12345678L);

        // Verificamos que el titular de la cuenta se haya actualizado correctamente
        assertEquals(clienteActualizado, cuenta.getTitular());
        verify(cuentaDao, times(1)).save(cuenta);
    }

    @Test
    public void testCuentaSoportada() {
        Cuenta cuenta = new Cuenta();
        cuenta.setTipoCuenta(TipoCuenta.CAJA_AHORRO);
        cuenta.setTipoMoneda(TipoMoneda.ARS);

        boolean resultado = cuentaService.cuentaSoportada(cuenta);

        // Verificar que la cuenta es soportada
        assertTrue(resultado);
    }

    @Test
    public void testCuentaNoSoportada() {
        Cuenta cuenta = new Cuenta();
        cuenta.setTipoCuenta(TipoCuenta.CUENTA_CORRIENTE);
        cuenta.setTipoMoneda(TipoMoneda.USD);

        boolean resultado = cuentaService.cuentaSoportada(cuenta);

        // Verificar que la cuenta no es soportada
        assertFalse(resultado);
    }

    @Test
    public void testTieneCuentaDeTipoMoneda() {
        Cuenta cuenta = new Cuenta();
        cuenta.setTipoMoneda(TipoMoneda.ARS);
        cuenta.setTipoCuenta(TipoCuenta.CAJA_AHORRO);

        // Crear cliente con la cuenta
        Cliente cliente = new Cliente();
        cliente.setDni(12345678L);

        when(cuentaDao.cuentasDelCliente(cliente.getDni())).thenReturn(Arrays.asList(cuenta));

        boolean resultado = cuentaService.tieneCuentaDeTipoMoneda(cliente.getDni(), TipoMoneda.ARS, TipoCuenta.CAJA_AHORRO);

        // Verificar que el cliente tiene la cuenta con tipo ARS y CAJA_AHORRO
        assertTrue(resultado);

    }

    @Test
    public void testNoTieneCuentaDeTipoMoneda() {
        Cuenta cuenta = new Cuenta();
        cuenta.setTipoMoneda(TipoMoneda.USD);
        cuenta.setTipoCuenta(TipoCuenta.CAJA_AHORRO);

        Cliente cliente = new Cliente();
        cliente.setDni(12345678L);

        when(cuentaDao.cuentasDelCliente(cliente.getDni())).thenReturn(Arrays.asList(cuenta));

        boolean resultado = cuentaService.tieneCuentaDeTipoMoneda(cliente.getDni(), TipoMoneda.ARS, TipoCuenta.CAJA_AHORRO);

        // Verificar que el cliente no tiene la cuenta con tipo ARS y CAJA_AHORRO
        assertFalse(resultado);
    }

    @Test
    public void testObtenerCuentaParaPrestamo() {
        Cuenta cuenta = new Cuenta();
        cuenta.setTipoMoneda(TipoMoneda.ARS);
        cuenta.setTipoCuenta(TipoCuenta.CUENTA_CORRIENTE);

        Cliente cliente = new Cliente();
        cliente.setDni(12345678L);

        when(cuentaDao.cuentasDelCliente(cliente.getDni())).thenReturn(Arrays.asList(cuenta));

        Cuenta cuentaObtenida = cuentaService.obtenerCuentaParaPrestamo(cliente.getDni(), TipoMoneda.ARS);

        // Verificar que la cuenta obtenida sea la correcta
        assertEquals(cuenta, cuentaObtenida);
    }

    @Test
    public void testNoObtenerCuentaParaPrestamo() {
        Cuenta cuenta = new Cuenta();
        cuenta.setTipoMoneda(TipoMoneda.USD);
        cuenta.setTipoCuenta(TipoCuenta.CAJA_AHORRO);

        Cliente cliente = new Cliente();
        cliente.setDni(12345678L);

        when(cuentaDao.cuentasDelCliente(cliente.getDni())).thenReturn(Arrays.asList(cuenta));

        Cuenta cuentaObtenida = cuentaService.obtenerCuentaParaPrestamo(cliente.getDni(), TipoMoneda.ARS);

        // Verificar que no se obtiene la cuenta porque la moneda no coincide
        assertNull(cuentaObtenida);
    }

}
