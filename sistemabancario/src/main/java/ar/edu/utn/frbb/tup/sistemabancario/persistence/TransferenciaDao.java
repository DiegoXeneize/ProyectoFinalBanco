package ar.edu.utn.frbb.tup.sistemabancario.persistence;

import ar.edu.utn.frbb.tup.sistemabancario.model.Movimientos;

import java.util.List;

public interface TransferenciaDao {
    void save(Movimientos movimientos);
    List<Movimientos> findByNumeroCuenta(long numeroCuenta);
}
