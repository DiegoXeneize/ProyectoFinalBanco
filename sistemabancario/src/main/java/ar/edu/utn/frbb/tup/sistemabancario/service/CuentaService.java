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
}
