package ar.edu.utn.frbb.tup.sistemabancario.service.implementation;

import ar.edu.utn.frbb.tup.sistemabancario.controller.dto.PrestamoDto;
import ar.edu.utn.frbb.tup.sistemabancario.controller.dto.PrestamoResponseDto;
import ar.edu.utn.frbb.tup.sistemabancario.controller.dto.PrestamosClienteResponseDto;
import ar.edu.utn.frbb.tup.sistemabancario.controller.dto.ConsultaPrestamoDto;
import ar.edu.utn.frbb.tup.sistemabancario.controller.dto.PlanPagoDto;
import ar.edu.utn.frbb.tup.sistemabancario.model.*;
import ar.edu.utn.frbb.tup.sistemabancario.model.exception.*;
import ar.edu.utn.frbb.tup.sistemabancario.persistence.implementation.ImplementsPrestamoDao;
import ar.edu.utn.frbb.tup.sistemabancario.service.implementation.ClienteServiceImplementation;
import ar.edu.utn.frbb.tup.sistemabancario.service.CuentaService;
import ar.edu.utn.frbb.tup.sistemabancario.service.PrestamoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PrestamoServiceImplementation implements PrestamoService {

    @Autowired
    private ImplementsPrestamoDao prestamoDao;

    @Autowired
    private ClienteServiceImplementation clienteService;

    @Autowired
    private CuentaService cuentaService;

    @Override
    public PrestamoResponseDto solicitarPrestamo(PrestamoDto prestamoDto) throws ClienteNoExistsException, CuentaNoEncontradaException, PrestamoException {
        // Validar si el cliente existe
        Cliente cliente = clienteService.buscarClientePorDni(prestamoDto.getNumeroCliente());
        if (cliente == null) {
            throw new ClienteNoExistsException("El cliente con ID " + prestamoDto.getNumeroCliente() + " no existe.");
        }

        // Verificar si el cliente tiene una cuenta en la moneda especificada
        Cuenta cuenta = cuentaService.obtenerCuentaParaPrestamo(prestamoDto.getNumeroCliente(), TipoMoneda.valueOf(prestamoDto.getMoneda().toUpperCase()));
        if (cuenta == null) {
            throw new CuentaNoEncontradaException("El cliente no tiene una cuenta en la moneda especificada.");
        }

        if (cuenta.getSaldo() < prestamoDto.getMontoPrestamo()) {
            throw new PrestamoException("El cliente no tiene suficiente saldo en su cuenta para solicitar el préstamo.");
        }

        if(prestamoDto.getPlazoMeses() < 1) {
            throw new PrestamoException("El plazo debe ser entre 1 y 12 meses.");
        }

        // Crear y configurar el préstamo
        Prestamo prestamo = new Prestamo();
        prestamo.setNumeroCliente(prestamoDto.getNumeroCliente());
        prestamo.setMonto(prestamoDto.getMontoPrestamo());
        prestamo.setMoneda(prestamoDto.getMoneda());
        prestamo.setPlazoMeses(prestamoDto.getPlazoMeses());
        prestamo.setInteres(calcularIntereses(prestamo.getMonto(), prestamo.getPlazoMeses()));
        prestamo.setMontoCuota(calcularMontoCuota(prestamo));
        prestamo.setPagosRealizados(0);
        prestamo.setSaldoRestante(prestamo.getMonto() + prestamo.getInteres());

        prestamoDao.save(prestamo);

        List<PlanPagoDto> planPagos = generarPlanPagos(prestamo);

        PrestamoResponseDto response = new PrestamoResponseDto();
        response.setEstado("APROBADO");
        response.setMensaje("El monto del préstamo fue acreditado en su cuenta.");
        response.setPlanPagos(planPagos);

        return response;
    }

    @Override
    public PrestamosClienteResponseDto obtenerPrestamosDeCliente(long numeroCliente) throws ClienteNoExistsException {

        Cliente cliente = clienteService.buscarClientePorDni(numeroCliente);
        if (cliente == null) {
            throw new ClienteNoExistsException("El cliente con ID " + numeroCliente + " no existe.");
        }

        // Obtener los préstamos del cliente
        List<Prestamo> prestamos = prestamoDao.findAllByCliente(numeroCliente);

        // Crear respuesta
        PrestamosClienteResponseDto response = new PrestamosClienteResponseDto();
        response.setNumeroCliente(numeroCliente);

        List<ConsultaPrestamoDto> prestamosDto = new ArrayList<>();
        for (Prestamo prestamo : prestamos) {
            ConsultaPrestamoDto dto = new ConsultaPrestamoDto();
            dto.setMonto(prestamo.getMonto());
            dto.setPlazoMeses(prestamo.getPlazoMeses());
            dto.setPagosRealizados(prestamo.getPagosRealizados());
            dto.setSaldoRestante(prestamo.getSaldoRestante());
            prestamosDto.add(dto);
        }

        response.setPrestamos(prestamosDto);
        return response;
    }


    private double calcularIntereses(double monto, int plazoMeses) {
        double tasaAnual = 0.05; // 5% anual
        return (monto * tasaAnual * plazoMeses) / 12;
    }

    private double calcularMontoCuota(Prestamo prestamo) {
        return (prestamo.getMonto() + prestamo.getInteres()) / prestamo.getPlazoMeses();
    }

    private List<PlanPagoDto> generarPlanPagos(Prestamo prestamo) {
        List<PlanPagoDto> planPagos = new ArrayList<>();
        for (int i = 1; i <= prestamo.getPlazoMeses(); i++) {
            planPagos.add(new PlanPagoDto(i, prestamo.getMontoCuota()));
        }
        return planPagos;
    }
}
