package ar.edu.utn.frbb.tup.sistemabancario.service;

import ar.edu.utn.frbb.tup.sistemabancario.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.sistemabancario.controller.dto.PrestamoDto;
import ar.edu.utn.frbb.tup.sistemabancario.model.Prestamo;
import ar.edu.utn.frbb.tup.sistemabancario.model.exception.ClienteNoExistsException;
import ar.edu.utn.frbb.tup.sistemabancario.model.exception.CuentaNoEncontradaException;

import java.util.List;

public interface PrestamoService {
    PrestamoResponseDto procesarSolicitudPrestamo(PrestamoDto prestamoDto) throws ClienteAlreadyExistsException, CuentaAlreadyExistsException;
    PrestamosClienteResponseDto obtenerPrestamosDeCliente(long numeroCliente) throws ClienteAlreadyExistsException;
}
