package ar.edu.utn.frbb.tup.sistemabancario.persistence.entity;

import ar.edu.utn.frbb.tup.sistemabancario.model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class CuentaEntityTest {

    @Test
    public void testCuentaEntityConversion() {
        Cliente cliente = new Cliente();
        cliente.setDni(42958843L);
        cliente.setNombre("Diego");
        cliente.setApellido("Bruno");

        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(1L);
        cuenta.setSaldo(1000.0);
        cuenta.setFechaAltaCuenta(LocalDate.of(2023, 1, 1));
        cuenta.setTitular(cliente);
        cuenta.setTipoMoneda(TipoMoneda.ARS);
        cuenta.setTipoCuenta(TipoCuenta.CAJA_AHORRO);

        CuentaEntity cuentaEntity = new CuentaEntity(cuenta);

        // Verificamos que los datos se hayan transferido correctamente
        assertEquals(cuenta.getNumeroCuenta(), cuentaEntity.getId());
        assertEquals(cuenta.getSaldo(), cuentaEntity.getSaldo());
        assertEquals(cuenta.getFechaAltaCuenta(), cuentaEntity.getFechaAltaCuenta());
        assertEquals(cuenta.getTitular(), cuentaEntity.getDniTitutar());
        assertEquals(cuenta.getTipoMoneda().toString(), cuentaEntity.getTipoMoneda());
        assertEquals(cuenta.getTipoCuenta().toString(), cuentaEntity.getTipoCuenta());
    }


    @Test
    public void testCuentaEntityEmptyMovimientos() {
        Cliente cliente = new Cliente();
        cliente.setDni(42958843L);
        cliente.setNombre("Diego");
        cliente.setApellido("Bruno");

        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(1L);
        cuenta.setSaldo(1000.0);
        cuenta.setFechaAltaCuenta(LocalDate.of(2023, 1, 12));
        cuenta.setTitular(cliente);
        cuenta.setTipoMoneda(TipoMoneda.ARS);
        cuenta.setTipoCuenta(TipoCuenta.CAJA_AHORRO);

        CuentaEntity cuentaEntity = new CuentaEntity(cuenta);

        // Verificamos que la lista de movimientos esté vacía
        assertTrue(cuentaEntity.getListaMovimientos().isEmpty());
    }
}
