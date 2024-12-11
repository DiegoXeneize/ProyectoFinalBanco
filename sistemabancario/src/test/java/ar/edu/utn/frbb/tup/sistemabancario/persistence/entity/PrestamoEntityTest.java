package ar.edu.utn.frbb.tup.sistemabancario.persistence.entity;

import ar.edu.utn.frbb.tup.sistemabancario.model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class PrestamoEntityTest {

    @Test
    public void testPrestamoEntityToPrestamoConversion() {
        Cliente cliente = new Cliente();
        cliente.setDni(42958843);
        cliente.setNombre("Diego");
        cliente.setApellido("Bruno");

        Prestamo prestamo = new Prestamo();
        prestamo.setId(1L);
        prestamo.setNumeroCliente(cliente.getDni());
        prestamo.setMonto(10000.0);
        prestamo.setMoneda("ARS");
        prestamo.setPlazoMeses(12);
        prestamo.setInteres(500.0);
        prestamo.setMontoCuota(850.0);
        prestamo.setPagosRealizados(2);
        prestamo.setSaldoRestante(9000.0);
        prestamo.setFechaAltaPrestamo(LocalDate.of(2023, 1, 12));

        Cuota cuota1 = new Cuota();
        cuota1.setCuotaNro(1);
        cuota1.setMonto(850.0);
        Cuota cuota2 = new Cuota();
        cuota2.setCuotaNro(2);
        cuota2.setMonto(850.0);

        prestamo.setPlanPagos(Arrays.asList(cuota1, cuota2));

        PrestamoEntity prestamoEntity = new PrestamoEntity(prestamo);

        // Verificamos que la conversión de Prestamo a PrestamoEntity se haya hecho bien
        assertEquals(prestamo.getId(), prestamoEntity.getId());
        assertEquals(prestamo.getNumeroCliente(), prestamoEntity.getNumeroCliente());
        assertEquals(prestamo.getMonto(), prestamoEntity.getMonto());
        assertEquals(prestamo.getMoneda(), prestamoEntity.getMoneda());
        assertEquals(prestamo.getPlazoMeses(), prestamoEntity.getPlazoMeses());
        assertEquals(prestamo.getInteres(), prestamoEntity.getInteres());
        assertEquals(prestamo.getMontoCuota(), prestamoEntity.getMontoCuota());
        assertEquals(prestamo.getPagosRealizados(), prestamoEntity.getPagosRealizados());
        assertEquals(prestamo.getSaldoRestante(), prestamoEntity.getSaldoRestante());
        assertEquals(prestamo.getFechaAltaPrestamo(), prestamoEntity.getFechaAltaPrestamo());
        assertNotNull(prestamoEntity.getPlanPagos());
        assertEquals(2, prestamoEntity.getPlanPagos().size());
        assertEquals(cuota1.getCuotaNro(), prestamoEntity.getPlanPagos().get(0).getCuotaNro());
        assertEquals(cuota2.getCuotaNro(), prestamoEntity.getPlanPagos().get(1).getCuotaNro());
    }


    @Test
    public void testPrestamoEntityEmptyPlanPagos() {
        Prestamo prestamo = new Prestamo();
        prestamo.setId(1L);
        prestamo.setNumeroCliente(42958843);
        prestamo.setMonto(10000.0);
        prestamo.setMoneda("ARS");
        prestamo.setPlazoMeses(12);
        prestamo.setInteres(500.0);
        prestamo.setMontoCuota(850.0);
        prestamo.setPagosRealizados(2);
        prestamo.setSaldoRestante(9000.0);
        prestamo.setFechaAltaPrestamo(LocalDate.of(2023, 1, 12));
        prestamo.setPlanPagos(new ArrayList<>()); // plan vacío

        PrestamoEntity prestamoEntity = new PrestamoEntity(prestamo);

        // Verificamos que el plan de pagos esté vacío
        assertNotNull(prestamoEntity.getPlanPagos());
        assertTrue(prestamoEntity.getPlanPagos().isEmpty());
    }

}
