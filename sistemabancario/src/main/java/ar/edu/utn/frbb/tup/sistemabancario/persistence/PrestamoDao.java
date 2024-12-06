package ar.edu.utn.frbb.tup.sistemabancario.persistence;

import ar.edu.utn.frbb.tup.sistemabancario.model.Prestamo;

public interface PrestamoDao {
    void save(Prestamo prestamo);
    // Otros métodos según necesidad
}
