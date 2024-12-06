package ar.edu.utn.frbb.tup.sistemabancario.persistence;

import ar.edu.utn.frbb.tup.sistemabancario.model.Cliente;


public interface ClienteDao {

    Cliente find(long dni, boolean cargarCuentas);
    void save(Cliente cliente);
}
