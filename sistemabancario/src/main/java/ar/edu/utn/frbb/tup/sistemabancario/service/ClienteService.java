package ar.edu.utn.frbb.tup.sistemabancario.service;

import ar.edu.utn.frbb.tup.sistemabancario.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.sistemabancario.model.Cliente;
import ar.edu.utn.frbb.tup.sistemabancario.model.Cuenta;
import ar.edu.utn.frbb.tup.sistemabancario.model.exception.*;

public interface ClienteService {

    void darAltaCliente(ClienteDto clienteDto) throws ClienteAlreadyExistsException, MenorEdadException;
    Cliente buscarClientePorDni(long dni) throws ClienteNoExistsException;
    void updateCliente(ClienteDto clienteDto, long dniAntiguo) throws ClienteNoExistsException, MenorEdadException, CuentaNoEncontradaException;
    void agregarCuentasAlCliente(Cuenta cuenta, long dni) throws TipoCuentaAlreadyExistsException, ClienteNoExistsException;
}
