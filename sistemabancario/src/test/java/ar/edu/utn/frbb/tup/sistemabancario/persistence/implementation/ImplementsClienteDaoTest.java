package ar.edu.utn.frbb.tup.sistemabancario.persistence.implementation;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.edu.utn.frbb.tup.sistemabancario.model.Cliente;
import ar.edu.utn.frbb.tup.sistemabancario.persistence.AbstractDataBase;

@ExtendWith(MockitoExtension.class)
public class ImplementsClienteDaoTest {

    @Mock
    private ImplementsClienteDao clienteDao;

    @Mock
    private ImplementsCuentaDao cuentaDao;

    @Mock
    private AbstractDataBase inMemoryDatabase;

    @Test
    public void testSaveCliente() {
        Cliente cliente = new Cliente();
        cliente.setDni(42958843L);
        cliente.setNombre("Diego");
        cliente.setApellido("Bruno");

        clienteDao.save(cliente);

        // Verificamos que el cliente fue guardado
        verify(clienteDao, times(1)).save(cliente);
    }


    @Test
    public void testFindCliente() {
        Cliente cliente = new Cliente();
        cliente.setDni(42958843L);
        cliente.setNombre("Diego");
        cliente.setApellido("Bruno");

        when(clienteDao.find(cliente.getDni(), false)).thenReturn(cliente);

        // Verificamos que el cliente es encontrado
        Cliente clienteEncontrado = clienteDao.find(cliente.getDni(), false);
        assertNotNull(clienteEncontrado);
        assertEquals(cliente.getDni(), clienteEncontrado.getDni());
    }

    @Test
    public void testFindClienteNoExistente() {
        when(clienteDao.find(42958843L, false)).thenReturn(null);

        // Verificar que el cliente no existe
        Cliente cliente = clienteDao.find(42958843L, false);
        assertNull(cliente);
    }


}
