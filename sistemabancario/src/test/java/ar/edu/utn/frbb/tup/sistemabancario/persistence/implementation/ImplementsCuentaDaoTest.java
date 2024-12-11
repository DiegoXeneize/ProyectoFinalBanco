package ar.edu.utn.frbb.tup.sistemabancario.persistence.implementation;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.edu.utn.frbb.tup.sistemabancario.model.Cliente;
import ar.edu.utn.frbb.tup.sistemabancario.model.Cuenta;
import ar.edu.utn.frbb.tup.sistemabancario.service.CuentaService;

@ExtendWith(MockitoExtension.class)
public class ImplementsCuentaDaoTest {

    @Mock
    private ImplementsCuentaDao cuentaDao;

    @Mock
    private CuentaService cuentaService;

    @Test
    public void testSaveCuenta() {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(1234L);
        cuenta.setSaldo(0);

        cuentaDao.save(cuenta);

        // Verificamos que la cuenta fue guardada
        verify(cuentaDao, times(1)).save(cuenta);
    }

    @Test
    public void testFindCuenta() {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(1234L);
        cuenta.setSaldo(0);

        when(cuentaDao.find(cuenta.getNumeroCuenta(), false)).thenReturn(cuenta);

        // Verificamos que la cuenta es encontrada
        Cuenta cuentaEncontrada = cuentaDao.find(cuenta.getNumeroCuenta(), false);
        assertNotNull(cuentaEncontrada);
        assertEquals(cuenta.getNumeroCuenta(), cuentaEncontrada.getNumeroCuenta());
    }

    @Test
    public void testFindCuentaNoExistente() {
        when(cuentaDao.find(1L, false)).thenReturn(null);

        // Verificamos que la cuenta no existe
        Cuenta cuenta = cuentaDao.find(1L, false);
        assertNull(cuenta);
    }

    @Test
    public void testCuentasDelCliente() {
        Cliente cliente = new Cliente();
        cliente.setDni(42958843L);

        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(1L);
        cuenta.setSaldo(0);

        when(cuentaDao.cuentasDelCliente(cliente.getDni())).thenReturn(Arrays.asList(cuenta));

        // Verificamos que las cuentas del cliente son correctas
        List<Cuenta> cuentas = cuentaDao.cuentasDelCliente(cliente.getDni());
        assertNotNull(cuentas);
        assertEquals(1, cuentas.size());
        assertEquals(cuenta.getNumeroCuenta(), cuentas.get(0).getNumeroCuenta());
    }

}
