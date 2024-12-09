package ar.edu.utn.frbb.tup.sistemabancario.service.implementation;

import ar.edu.utn.frbb.tup.sistemabancario.controller.dto.PrestamoDto;
import ar.edu.utn.frbb.tup.sistemabancario.controller.dto.PrestamoResponseDto;
import ar.edu.utn.frbb.tup.sistemabancario.controller.dto.PrestamosClienteResponseDto;
import ar.edu.utn.frbb.tup.sistemabancario.controller.dto.ConsultaPrestamoDto;
import ar.edu.utn.frbb.tup.sistemabancario.controller.dto.PagoCuotaDto;
import ar.edu.utn.frbb.tup.sistemabancario.controller.dto.PagoCuotaResponseDto;
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

    @Autowired
    private CreditRatingService creditRatingService;

    private static long prestamoIdCounter = 1;

    private long generateUniquePrestamoId() {
        return prestamoIdCounter++;
    }

    @Override
    public PrestamoResponseDto solicitarPrestamo(PrestamoDto prestamoDto) throws ClienteNoExistsException, CuentaNoEncontradaException, PrestamoException {

        Cliente cliente = clienteService.buscarClientePorDni(prestamoDto.getNumeroCliente());
        if (cliente == null) {
            throw new ClienteNoExistsException("El cliente con ID " + prestamoDto.getNumeroCliente() + " no existe.");
        }

        Cuenta cuenta = cuentaService.obtenerCuentaParaPrestamo(prestamoDto.getNumeroCliente(), TipoMoneda.valueOf(prestamoDto.getMoneda().toUpperCase()));
        if (cuenta == null) {
            throw new CuentaNoEncontradaException("El cliente no tiene una cuenta en la moneda especificada.");
        }

        if (!creditRatingService.tieneBuenHistorialCrediticio(prestamoDto.getNumeroCliente())) {
            throw new PrestamoException("El cliente con DNI " + prestamoDto.getNumeroCliente() + " no tiene buen historial crediticio.");
        }

        if (prestamoDto.getPlazoMeses() < 1) {
            throw new PrestamoException("El plazo debe ser mayor a 0.");
        }

        Prestamo prestamo = new Prestamo();
        prestamo.setId(generateUniquePrestamoId());
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

        List<Prestamo> prestamos = prestamoDao.findAllByCliente(numeroCliente);

        PrestamosClienteResponseDto response = new PrestamosClienteResponseDto();
        response.setNumeroCliente(numeroCliente);

        List<ConsultaPrestamoDto> prestamosDto = new ArrayList<>();
        for (Prestamo prestamo : prestamos) {
            ConsultaPrestamoDto dto = new ConsultaPrestamoDto();
            dto.setIdPrestamo(prestamo.getId());
            dto.setMonto(prestamo.getMonto());
            dto.setPlazoMeses(prestamo.getPlazoMeses());
            dto.setPagosRealizados(prestamo.getPagosRealizados());
            dto.setSaldoRestante(prestamo.getSaldoRestante());
            prestamosDto.add(dto);
        }

        response.setPrestamos(prestamosDto);
        return response;
    }


    @Override
    public PagoCuotaResponseDto ejecutarPagoDeCuota(PagoCuotaDto pagoCuotaDto) throws PrestamoNoEncontradoException {

        Prestamo prestamo = prestamoDao.find(pagoCuotaDto.getNumeroPrestamo());
        if (prestamo == null) {
            throw new PrestamoNoEncontradoException("El préstamo con número " + pagoCuotaDto.getNumeroPrestamo() + " no existe.");
        }

        if (prestamo.getSaldoRestante() <= 0) {
            throw new IllegalArgumentException("El préstamo con número " + pagoCuotaDto.getNumeroPrestamo() + " ya está saldado.");
        }

        if (pagoCuotaDto.getCantidadCuotas() <= 0) {
            throw new IllegalArgumentException("La cantidad de cuotas a pagar debe ser mayor que 0.");
        }

        int cuotasRestantes = prestamo.getPlazoMeses() - prestamo.getPagosRealizados();
        if (pagoCuotaDto.getCantidadCuotas() > cuotasRestantes) {
            throw new IllegalArgumentException("El número de cuotas excede las cuotas restantes del préstamo.");
        }

        double montoPorCuota = prestamo.getMontoCuota();
        double montoTotalPago = montoPorCuota * pagoCuotaDto.getCantidadCuotas();

        if (pagoCuotaDto.getMontoPago() < montoTotalPago) {
            throw new IllegalArgumentException("El monto proporcionado no es suficiente para pagar las cuotas seleccionadas. Monto requerido: " + montoTotalPago);
        }

        long numeroCliente = prestamo.getNumeroCliente();

        List<Cuenta> cuentasCliente = cuentaService.listCuentasByCliente(numeroCliente);
        if (cuentasCliente.isEmpty()) {
            throw new IllegalArgumentException("El cliente con número " + numeroCliente + " no tiene cuentas asociadas.");
        }

        Cuenta cuentaSeleccionada = null;
        for (Cuenta cuenta : cuentasCliente) {
            if (cuenta.getTipoMoneda().getText().equalsIgnoreCase(prestamo.getMoneda())) {
                cuentaSeleccionada = cuenta;
                break;
            }
        }

        if (cuentaSeleccionada == null) {
            throw new IllegalArgumentException("El cliente no tiene una cuenta en la moneda requerida (" + prestamo.getMoneda() + ").");
        }

        if (!cuentaService.tieneSaldoDisponible(cuentaSeleccionada, montoTotalPago)) {
            throw new IllegalArgumentException("No tiene suficiente saldo en su cuenta para realizar este pago.");
        }

        prestamo.setPagosRealizados(prestamo.getPagosRealizados() + pagoCuotaDto.getCantidadCuotas());
        prestamo.setSaldoRestante(prestamo.getSaldoRestante() - montoTotalPago);
        prestamoDao.save(prestamo);

        cuentaService.actualizarSaldo(cuentaSeleccionada.getNumeroCuenta(), cuentaSeleccionada.getSaldo() - montoTotalPago);

        PagoCuotaResponseDto response = new PagoCuotaResponseDto();
        response.setNumeroPrestamo(prestamo.getId());
        response.setEstado("COMPLETADO");
        response.setMensaje("El pago de " + pagoCuotaDto.getCantidadCuotas() + " cuota(s) fue registrado exitosamente. Saldo restante: " + prestamo.getSaldoRestante());

        return response;
    }




    private double calcularIntereses(double monto, int plazoMeses) {
        double tasaAnual = 0.05; 
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
