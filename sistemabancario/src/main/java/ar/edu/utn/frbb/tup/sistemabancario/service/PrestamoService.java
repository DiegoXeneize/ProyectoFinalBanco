package ar.edu.utn.frbb.tup.sistemabancario.service;

import ar.edu.utn.frbb.tup.sistemabancario.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.sistemabancario.controller.dto.PrestamoDto;
import ar.edu.utn.frbb.tup.sistemabancario.model.Prestamo;
import ar.edu.utn.frbb.tup.sistemabancario.model.exception.ClienteNoExistsException;
import ar.edu.utn.frbb.tup.sistemabancario.model.exception.CuentaNoEncontradaException;

import java.util.List;

public interface PrestamoService {
    Prestamo solicitarPrestamo(PrestamoDto prestamoDto) throws ClienteNoExistsException, CuentaNoEncontradaException;
    List<Prestamo> listPrestamosByCliente(long dniTitular);
}
