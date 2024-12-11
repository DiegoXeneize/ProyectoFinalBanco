package ar.edu.utn.frbb.tup.sistemabancario.persistence.implementation;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.edu.utn.frbb.tup.sistemabancario.model.Prestamo;
import ar.edu.utn.frbb.tup.sistemabancario.persistence.implementation.ImplementsPrestamoDao;

@ExtendWith(MockitoExtension.class)
public class ImplementsPrestamoDaoTest {

    @Mock
    private ImplementsPrestamoDao prestamoDao;

    @Test
    public void testSavePrestamo() {
        Prestamo prestamo = new Prestamo();
        prestamo.setId(1L);
        prestamo.setNumeroCliente(42958843L);

        prestamoDao.save(prestamo);

        // Verificamos que el préstamo fue guardado
        verify(prestamoDao, times(1)).save(prestamo);
    }

    @Test
    public void testFindPrestamo() {
        Prestamo prestamo = new Prestamo();
        prestamo.setId(1L);
        prestamo.setNumeroCliente(42958843L);

        when(prestamoDao.find(prestamo.getId())).thenReturn(prestamo);

        // Verificamos que el préstamo es encontrado
        Prestamo prestamoEncontrado = prestamoDao.find(prestamo.getId());
        assertNotNull(prestamoEncontrado);
        assertEquals(prestamo.getId(), prestamoEncontrado.getId());
    }

    @Test
    public void testFindAllByCliente() {
        Prestamo prestamo = new Prestamo();
        prestamo.setId(1L);
        prestamo.setNumeroCliente(42958843L);

        when(prestamoDao.findAllByCliente(42958843L)).thenReturn(Arrays.asList(prestamo));

        // Verificamos que los préstamos del cliente son correctos
        List<Prestamo> prestamos = prestamoDao.findAllByCliente(42958843L);
        assertNotNull(prestamos);
        assertEquals(1, prestamos.size());
        assertEquals(prestamo.getId(), prestamos.get(0).getId());
    }
}