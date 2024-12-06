package ar.edu.utn.frbb.tup.sistemabancario.service;

import ar.edu.utn.frbb.tup.sistemabancario.model.Cuenta;
import ar.edu.utn.frbb.tup.sistemabancario.model.Movimientos;
import ar.edu.utn.frbb.tup.sistemabancario.model.exception.CantidadNegativaException;
import ar.edu.utn.frbb.tup.sistemabancario.model.exception.ClienteNoExistsException;
import ar.edu.utn.frbb.tup.sistemabancario.model.exception.CuentaNoEncontradaException;
import ar.edu.utn.frbb.tup.sistemabancario.model.exception.NoAlcanzaException;

public interface TransferenciaService {

    Cuenta verificarCuenta(long numeroCuenta) throws CuentaNoEncontradaException;
    void realizarMovimiento(Movimientos movimientos, Cuenta cuenta) throws CuentaNoEncontradaException;
    void transferencia(long cuentaOrigen, long cuentaDestino, double monto) throws CantidadNegativaException, NoAlcanzaException, CuentaNoEncontradaException, ClienteNoExistsException;
    double consultaSaldo(long numeroCuenta) throws CuentaNoEncontradaException;

}
