package ar.edu.utn.frbb.tup.sistemabancario.service;

import ar.edu.utn.frbb.tup.sistemabancario.controller.dto.PagoCuotaDto;
import ar.edu.utn.frbb.tup.sistemabancario.controller.dto.PagoCuotaResponseDto;
import ar.edu.utn.frbb.tup.sistemabancario.controller.dto.PrestamoDto;
import ar.edu.utn.frbb.tup.sistemabancario.controller.dto.PrestamoResponseDto;
import ar.edu.utn.frbb.tup.sistemabancario.controller.dto.PrestamosClienteResponseDto;
import ar.edu.utn.frbb.tup.sistemabancario.model.exception.ClienteNoExistsException;
import ar.edu.utn.frbb.tup.sistemabancario.model.exception.CuentaNoEncontradaException;
import ar.edu.utn.frbb.tup.sistemabancario.model.exception.PrestamoException;
import ar.edu.utn.frbb.tup.sistemabancario.model.exception.PrestamoNoEncontradoException;


public interface PrestamoService {
    PrestamoResponseDto solicitarPrestamo(PrestamoDto prestamoDto) throws ClienteNoExistsException, CuentaNoEncontradaException, PrestamoException;
    PrestamosClienteResponseDto obtenerPrestamosDeCliente(long numeroCliente);
    PagoCuotaResponseDto ejecutarPagoDeCuota(PagoCuotaDto pagoCuotaDto) throws PrestamoNoEncontradoException;
}

