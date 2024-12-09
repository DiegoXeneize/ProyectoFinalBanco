package ar.edu.utn.frbb.tup.sistemabancario.persistence;

import ar.edu.utn.frbb.tup.sistemabancario.model.Cuenta;

import java.util.List;

public interface CuentaDao {

    Cuenta find(long id, boolean cargarMovimientos);
    void save(Cuenta cuenta);
    List<Cuenta> cuentasDelCliente(long dni);
    void updateSaldo(long numeroCuenta, double nuevoSaldo);

}
