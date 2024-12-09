package ar.edu.utn.frbb.tup.sistemabancario.persistence;

import java.util.List;

import ar.edu.utn.frbb.tup.sistemabancario.model.Prestamo;

public interface PrestamoDao {
    void save(Prestamo prestamo);
    List<Prestamo> findAllByCliente(long numeroCliente);
    Prestamo find(long id);
}