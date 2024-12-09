package ar.edu.utn.frbb.tup.sistemabancario.service;

import ar.edu.utn.frbb.tup.sistemabancario.controller.dto.CuentaDto;
import ar.edu.utn.frbb.tup.sistemabancario.model.Cuenta;
import ar.edu.utn.frbb.tup.sistemabancario.model.TipoMoneda;
import ar.edu.utn.frbb.tup.sistemabancario.model.exception.*;

import java.util.List;

public interface CuentaService {
    void darAltaCuenta(CuentaDto cuentaDto, long dni) throws CuentaAlreadyExistsException, TipoCuentaNotSupportedException, TipoCuentaAlreadyExistsException, ClienteNoExistsException;
    List<Cuenta> listCuentasByCliente(long dniTitular) throws CuentaNoEncontradaException;
    Cuenta obtenerCuentaParaPrestamo(long numeroCliente, TipoMoneda moneda) throws CuentaNoEncontradaException;
    double depositar(long numeroCuenta, double monto) throws CuentaNoEncontradaException, IllegalArgumentException;
    void actualizarSaldo(long numeroCuenta, double nuevoSaldo) throws CuentaNoEncontradaException; 
    Cuenta buscarCuentaPorNumero(long numeroCuenta) throws CuentaNoEncontradaException;
    boolean tieneSaldoDisponible(Cuenta cuenta, double montoCuota);
}
