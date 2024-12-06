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
    public PrestamoResponseDto procesarSolicitudPrestamo(PrestamoDto prestamoDto) throws ClienteAlreadyExistsException, CuentaAlreadyExistsException {
        Prestamo prestamo = new Prestamo(prestamoDto);
        PrestamoResponseDto response = new PrestamoResponseDto();
    
        if (!cuentaDao.existeCuenta(prestamo.getNumeroCliente(), prestamo.getMoneda())) {
            throw new CuentaAlreadyExistsException("El cliente no tiene una cuenta en la moneda especificada.");
        }
    
        if (!clienteDao.existeCliente(prestamoDto.getNumeroCliente())) {
            throw new ClienteAlreadyExistsException("El cliente no existe.");
        }
    
        ScoreCrediticioService scoreService = new ScoreCrediticioService();
        boolean scoreAprobado = scoreService.validarScore(prestamoDto);
    
        if (!scoreAprobado) {
            prestamo.setEstado("Rechazado");
            prestamoDao.guardar(prestamo);
    
            response.setEstado("Rechazado");
            response.setMensaje("El cliente no tiene un score crediticio suficiente para solicitar el préstamo.");
            return response;
        }
    
        double tasaInteresAnual = 0.05;
        double intereses = (prestamo.getPlazo() / 12) * tasaInteresAnual * prestamo.getMonto();
        double montoTotal = prestamo.getMonto() + intereses;
        double montoCuota = montoTotal / prestamo.getPlazo();
    
        prestamo.setEstado("Aprobado");
        prestamo.setInteres(intereses);
        prestamo.setMontoCuota(montoCuota);
        prestamoDao.guardar(prestamo);

        cuentaService.depositar(prestamo.getNumeroCliente(), prestamo.getMoneda(), prestamo.getMonto());
    
        List<PlanPagoDto> planPagos = generarPlanDePagos(prestamo.getPlazo(), montoCuota);

        response.setEstado("Aprobado");
        response.setMensaje("El monto del préstamo fue acreditado en su cuenta.");
        response.setPlanPagos(planPagos);
    
        return response;
    }
    
    private List<PlanPagoDto> generarPlanDePagos(int plazo, double montoCuota) {
        List<PlanPagoDto> planPagos = new ArrayList<>();
        for (int i = 1; i <= plazo; i++) {
            planPagos.add(new PlanPagoDto(i, montoCuota));
        }
        return planPagos;
    }
    
    
    @Override
    public PrestamosClienteResponseDto obtenerPrestamosDeCliente(long numeroCliente) throws ClienteAlreadyExistsException {
        // Validar existencia del cliente
        validarClienteExistente(numeroCliente);
    
        // Obtener préstamos del cliente
        List<Prestamo> prestamosCliente = prestamoDao.buscarPrestamosPorCliente(numeroCliente);
    
        // Mapear los préstamos a DTO
        List<ConsultaPrestamoDto> prestamosDto = prestamosCliente.stream()
                .map(this::convertirAPrestamoDto)
                .toList();
    
        // Crear respuesta
        return construirRespuestaPrestamos(numeroCliente, prestamosDto);
    }
    
    private void validarClienteExistente(long numeroCliente) throws ClienteAlreadyExistsException {
        if (clienteDao.find(numeroCliente) == null) {
            throw new ClienteAlreadyExistsException("El cliente con número " + numeroCliente + " no existe.");
        }
    }
    
    private ConsultaPrestamoDto convertirAPrestamoDto(Prestamo prestamo) {
        double saldoRestante = calcularSaldoRestante(prestamo);
        return new ConsultaPrestamoDto(
                prestamo.getId(),
                prestamo.getMoneda(),
                prestamo.getMonto(),
                prestamo.getPlazoMeses(),
                prestamo.getPagosRealizados(),
                saldoRestante
        );
    }
    
    private double calcularSaldoRestante(Prestamo prestamo) {
        double pagosRealizados = prestamo.getPagosRealizados() * prestamo.getMontoCuota();
        return prestamo.getMonto() - pagosRealizados;
    }
    
    private PrestamosClienteResponseDto construirRespuestaPrestamos(long numeroCliente, List<ConsultaPrestamoDto> prestamosDto) {
        PrestamosClienteResponseDto response = new PrestamosClienteResponseDto();
        response.setNumeroCliente(numeroCliente);
        response.setConsultaPrestamos(prestamosDto);
        return response;
    }
    


}
