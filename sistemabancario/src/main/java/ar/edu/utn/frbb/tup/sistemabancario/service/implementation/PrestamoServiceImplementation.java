package ar.edu.utn.frbb.tup.sistemabancario.service.implementation;

import ar.edu.utn.frbb.tup.sistemabancario.controller.dto.PrestamoDto;
import ar.edu.utn.frbb.tup.sistemabancario.model.*;
import ar.edu.utn.frbb.tup.sistemabancario.model.exception.ClienteNoExistsException;
import ar.edu.utn.frbb.tup.sistemabancario.model.exception.CuentaNoEncontradaException;
import ar.edu.utn.frbb.tup.sistemabancario.persistence.implementation.ImplementsPrestamoDao;
import ar.edu.utn.frbb.tup.sistemabancario.service.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrestamoServiceImplementation implements PrestamoService {

    @Autowired
    private ClienteServiceImplementation clienteService;

    @Autowired CuentaServiceImplementation cuentaService;

    @Autowired
    private ImplementsPrestamoDao prestamoDao;

    @Override
    public Prestamo solicitarPrestamo(PrestamoDto prestamoDto) throws ClienteNoExistsException, CuentaNoEncontradaException {
        Cliente cliente = clienteService.buscarClientePorDni(prestamoDto.getNumeroCliente());
        Cuenta cuenta = cuentaService.obtenerCuentaParaPrestamo(cliente.getDni(), TipoMoneda.fromString(prestamoDto.getMoneda()));

        if (cuenta == null) {
            throw new CuentaNoEncontradaException("El cliente no tiene una cuenta en la moneda especificada.");
        }

        Prestamo prestamo = new Prestamo(cliente, prestamoDto.getMontoPrestamo(), cuenta.getTipoMoneda(), prestamoDto.getPlazoMeses());
        prestamoDao.save(prestamo);

        cuenta.setSaldo(cuenta.getSaldo() + prestamoDto.getMontoPrestamo());

        return prestamo;
    }

    @Override
    public List<Prestamo> listPrestamosByCliente(long dniTitular) {
        return List.of();
    }


}
