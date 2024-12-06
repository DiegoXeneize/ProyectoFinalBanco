package ar.edu.utn.frbb.tup.sistemabancario.service.implementation;


import ar.edu.utn.frbb.tup.sistemabancario.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.sistemabancario.model.Cliente;
import ar.edu.utn.frbb.tup.sistemabancario.model.Cuenta;
import ar.edu.utn.frbb.tup.sistemabancario.model.exception.*;
import ar.edu.utn.frbb.tup.sistemabancario.persistence.implementation.ImplementsClienteDao;
import ar.edu.utn.frbb.tup.sistemabancario.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ClienteServiceImplementation implements ClienteService {

    @Autowired
    ImplementsClienteDao clienteDao;

    @Autowired
    CuentaServiceImplementation cuentaService;


    // -- Funcion que da de alta un cliente --
    @Override
    public void darAltaCliente(ClienteDto clienteDto) throws ClienteAlreadyExistsException, MenorEdadException {
        Cliente cliente = new Cliente(clienteDto);
        if(clienteDao.find(cliente.getDni(), false) != null){
            throw new ClienteAlreadyExistsException("El cliente con dni " + cliente.getDni() + " ya es usuario de nuestro banco");
        }
        if(cliente.getFechaNacimiento() == null){
            throw new NullPointerException("La fecha de nacimiento no puede ser nula");
        }
        if(cliente.getEdad() < 18){
            throw new MenorEdadException("El cliente no puede ser menor a 18 años");
        }
        clienteDao.save(cliente);
    }

    // -- Funcion que busca un cliente por dni --
    @Override
    public Cliente buscarClientePorDni(long dni) throws ClienteNoExistsException {
        Cliente cliente = clienteDao.find(dni, true);
        if(cliente == null){
            throw new ClienteNoExistsException("El cliente con dni " + dni + " no existe");
        }
        return cliente;
    }

    // -- Funcion que actualiza un cliente --
    @Override
    public void updateCliente(ClienteDto clienteDto, long dniAntiguo) throws ClienteNoExistsException, MenorEdadException, CuentaNoEncontradaException {

        Cliente clienteExistente = buscarClientePorDni(dniAntiguo);
        if(clienteExistente == null){
            throw new ClienteNoExistsException("El cliente con el dni proporcionada no es cliente de nuestro banco");
        }
        Cliente clienteActualizado = new Cliente(clienteDto);
        if(clienteActualizado.getFechaNacimiento() == null){
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser nula");
        }
        if(clienteActualizado.getEdad() < 18) {
            throw new MenorEdadException("El cliente no puede ser menor a 18 años");
        }
        if (!clienteExistente.getCuentasDelCliente().isEmpty()) {
            cuentaService.actualizarTitularCuenta(clienteActualizado, dniAntiguo);
        }
        clienteDao.deleteCliente(dniAntiguo);
        clienteDao.save(clienteActualizado);

    }

    // -- Funcion que agrega una cuenta a un cliente --
    @Override
    public void agregarCuentasAlCliente(Cuenta cuenta, long dni) throws TipoCuentaAlreadyExistsException, ClienteNoExistsException {
        Cliente cliente = buscarClientePorDni(dni);
        if(cuentaService.tieneCuentaDeTipoMoneda(cliente.getDni(), cuenta.getTipoMoneda(), cuenta.getTipoCuenta())){
            throw new TipoCuentaAlreadyExistsException("El cliente ya tiene cuenta del tipo " + cuenta.getTipoCuenta() + " y tipo moneda " + cuenta.getTipoMoneda());
        }
        cuenta.setTitular(cliente);
        cliente.setCuentasDelCliente(cuenta);

        clienteDao.save(cliente);
    }



}
