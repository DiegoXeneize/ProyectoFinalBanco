package ar.edu.utn.frbb.tup.sistemabancario.service.implementation;


import ar.edu.utn.frbb.tup.sistemabancario.controller.dto.CuentaDto;
import ar.edu.utn.frbb.tup.sistemabancario.model.*;
import ar.edu.utn.frbb.tup.sistemabancario.model.exception.*;
import ar.edu.utn.frbb.tup.sistemabancario.persistence.implementation.ImplementsCuentaDao;
import ar.edu.utn.frbb.tup.sistemabancario.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CuentaServiceImplementation implements CuentaService {

    @Autowired
    ImplementsCuentaDao cuentaDao;

    @Autowired
    ClienteServiceImplementation clienteService;

    @Override
    public void darAltaCuenta(CuentaDto cuentaDto, long dni) throws CuentaAlreadyExistsException, TipoCuentaNotSupportedException, TipoCuentaAlreadyExistsException, ClienteNoExistsException {
        Cuenta cuenta = toCuenta(cuentaDto);
        if(cuentaDao.find(cuenta.getNumeroCuenta(), false) != null){
            throw new CuentaAlreadyExistsException("El número de cuenta " + cuenta.getNumeroCuenta() + " ya existe");
        }
        if(!cuentaSoportada(cuenta)){
            throw new TipoCuentaNotSupportedException("La cuenta de tipo " + cuenta.getTipoCuenta() + " y tipo moneda " + cuenta.getTipoMoneda() + " no es soportada");
        }
        clienteService.agregarCuentasAlCliente(cuenta, dni);
        cuentaDao.save(cuenta);
    }

    @Override
    public List<Cuenta> listCuentasByCliente(long dniTitular) throws CuentaNoEncontradaException {
        List<Cuenta> cuentasDelCliente = cuentaDao.cuentasDelCliente(dniTitular);
        if(cuentasDelCliente.isEmpty()){
            throw new CuentaNoEncontradaException("El cliente no tiene cuentas");
        }
        return cuentasDelCliente;
    }

    @Override
    public double depositar(long numeroCuenta, double monto) throws CuentaNoEncontradaException, IllegalArgumentException {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a depositar debe ser mayor a 0.");
        }

        Cuenta cuenta = cuentaDao.find(numeroCuenta, true);
        if (cuenta == null) {
            throw new CuentaNoEncontradaException("No existe cuenta con el número " + numeroCuenta);
        }

        cuenta.setSaldo(cuenta.getSaldo() + monto);
        cuentaDao.save(cuenta);
        return cuenta.getSaldo();
    }

    @Override
    public void actualizarSaldo(long numeroCuenta, double nuevoSaldo) throws CuentaNoEncontradaException {
        Cuenta cuenta = cuentaDao.find(numeroCuenta, false);
        if (cuenta == null) {
            throw new CuentaNoEncontradaException("No existe cuenta con número " + numeroCuenta);
        }

        cuentaDao.updateSaldo(numeroCuenta, nuevoSaldo);
    }

    @Override
    public Cuenta buscarCuentaPorNumero(long numeroCuenta) throws CuentaNoEncontradaException {
        Cuenta cuenta = cuentaDao.find(numeroCuenta, true);
        if (cuenta == null) {
            throw new CuentaNoEncontradaException("No existe cuenta con número " + numeroCuenta);
        }
        return cuenta;
    }

    @Override
    public boolean tieneSaldoDisponible(Cuenta cuenta, double montoCuota) {
        return cuenta.getSaldo() >= montoCuota;
    }




    public void actualizarTitularCuenta(Cliente clienteActualizado, long dniAntiguo) throws CuentaNoEncontradaException {
        List<Cuenta> cuentasByCliente = listCuentasByCliente(dniAntiguo);
        for (Cuenta c : cuentasByCliente){
            c.setTitular(clienteActualizado);
            cuentaDao.save(c);
        }
    }


    public boolean cuentaSoportada(Cuenta cuenta){
        return switch (cuenta.getTipoCuenta()) {
            case CUENTA_CORRIENTE -> cuenta.getTipoMoneda() == TipoMoneda.ARS;
            case CAJA_AHORRO -> cuenta.getTipoMoneda() == TipoMoneda.ARS || cuenta.getTipoMoneda() == TipoMoneda.USD;
            default -> false;
        };
    }

    public boolean tieneCuentaDeTipoMoneda(long numeroCliente, TipoMoneda moneda, TipoCuenta tipoCuenta){
        List<Cuenta> cuentasDelCliente = cuentaDao.cuentasDelCliente(numeroCliente);
        for (Cuenta c : cuentasDelCliente){
           if(c.getTipoMoneda().equals(moneda) && c.getTipoCuenta().equals(tipoCuenta)){
               return true;
           }
        }
        return false;
    }


    public Cuenta obtenerCuentaParaPrestamo(long numeroCliente, TipoMoneda moneda){
        List<Cuenta> cuentasByCliente = cuentaDao.cuentasDelCliente(numeroCliente);
        for(Cuenta c : cuentasByCliente){
            if(c.getTipoMoneda().equals(moneda)){
                if(c.getTipoCuenta().equals(TipoCuenta.CUENTA_CORRIENTE)){
                    return c;
                }else if(c.getTipoCuenta().equals(TipoCuenta.CAJA_AHORRO)){
                    return  c;
                }
            }
        }
        return null;
    }


    private Cuenta toCuenta(CuentaDto cuentaDto){
        Cuenta cuenta = new Cuenta();
        cuenta.setTipoMoneda(cuentaDto.getTipoMoneda() != null ? TipoMoneda.fromString(cuentaDto.getTipoMoneda()) : null);
        cuenta.setTipoCuenta(cuentaDto.getTipoCuenta() != null ? TipoCuenta.fromString(cuentaDto.getTipoCuenta()) : null);
        return cuenta;
    }

}
