package ar.edu.utn.frbb.tup.sistemabancario.persistence.entity;

import ar.edu.utn.frbb.tup.sistemabancario.model.Cliente;
import ar.edu.utn.frbb.tup.sistemabancario.model.TipoPersona;
import ar.edu.utn.frbb.tup.sistemabancario.model.Cuenta;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteEntityTest {

    @Test
    public void testClienteEntityCreacionCorrecta() {
        Cliente cliente = new Cliente();
        cliente.setDni(12345678L);
        cliente.setNombre("Diego");
        cliente.setApellido("Bruno");
        cliente.setFechaNacimiento(LocalDate.of(2000, 9, 30));
        cliente.setFechaAltaCliente(LocalDate.of(2024, 1, 12));
        cliente.setBanco("Banco UTN");
        cliente.setTipoPersona(TipoPersona.PERSONA_FISICA);

        ClienteEntity clienteEntity = new ClienteEntity(cliente);

        // Verificamos que los datos se hayan transferido correctamente
        assertEquals(cliente.getDni(), clienteEntity.getId());
        assertEquals(cliente.getNombre(), clienteEntity.getNombre());
        assertEquals(cliente.getApellido(), clienteEntity.getApellido());
        assertEquals(cliente.getFechaNacimiento(), clienteEntity.getFechaNacimiento());
        assertEquals(cliente.getFechaAltaCliente(), clienteEntity.getFechaAltaCliente());
        assertEquals(cliente.getBanco(), clienteEntity.getBanco());
        assertEquals(cliente.getTipoPersona().getText(), clienteEntity.getTipoPersona());
    }


    @Test
    public void testClienteEntityConCuentasDelCliente() {
        Cliente cliente = new Cliente();
        cliente.setDni(42958843L);
        cliente.setNombre("Diego");
        cliente.setApellido("Bruno");
        cliente.setFechaNacimiento(LocalDate.of(2000, 9, 30));
        cliente.setFechaAltaCliente(LocalDate.of(2024, 1, 12));
        cliente.setBanco("Banco UTN");
        cliente.setTipoPersona(TipoPersona.PERSONA_FISICA);

        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(1L);
        cliente.setCuentasDelCliente(cuenta);

        ClienteEntity clienteEntity = new ClienteEntity(cliente);

        // Verificamos que las cuentas del cliente estén correctamente asignadas
        assertTrue(clienteEntity.getCuentasDelCliente().contains(1L));
    }
}
