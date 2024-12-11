package ar.edu.utn.frbb.tup.sistemabancario.service;

import ar.edu.utn.frbb.tup.sistemabancario.controller.dto.PrestamoDto;
import ar.edu.utn.frbb.tup.sistemabancario.controller.dto.PrestamoResponseDto;
import ar.edu.utn.frbb.tup.sistemabancario.controller.dto.PrestamosClienteResponseDto;
import ar.edu.utn.frbb.tup.sistemabancario.controller.dto.PagoCuotaDto;
import ar.edu.utn.frbb.tup.sistemabancario.controller.dto.PagoCuotaResponseDto;
import ar.edu.utn.frbb.tup.sistemabancario.model.*;
import ar.edu.utn.frbb.tup.sistemabancario.model.exception.ClienteNoExistsException;
import ar.edu.utn.frbb.tup.sistemabancario.model.exception.CuentaNoEncontradaException;
import ar.edu.utn.frbb.tup.sistemabancario.model.exception.PrestamoException;
import ar.edu.utn.frbb.tup.sistemabancario.model.exception.PrestamoNoEncontradoException;
import ar.edu.utn.frbb.tup.sistemabancario.persistence.implementation.ImplementsPrestamoDao;
import ar.edu.utn.frbb.tup.sistemabancario.service.implementation.ClienteServiceImplementation;
import ar.edu.utn.frbb.tup.sistemabancario.service.implementation.CreditRatingService;
import ar.edu.utn.frbb.tup.sistemabancario.service.implementation.PrestamoServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PrestamoServiceTest {

    @Mock
    private ImplementsPrestamoDao prestamoDao;

    @Mock
    private ClienteServiceImplementation clienteService;

    @Mock
    private CuentaService cuentaService;

    @Mock
    private CreditRatingService creditRatingService;

    @InjectMocks
    private PrestamoServiceImplementation prestamoService;

    private PrestamoDto prestamoDto;
    private Cliente clienteMock;
    private Cuenta cuentaMock;
    private Prestamo prestamoMock;

    @BeforeEach
    public void setUp() {
        prestamoDto = new PrestamoDto();
        prestamoDto.setNumeroCliente(42958843L);
        prestamoDto.setMontoPrestamo(10000);
        prestamoDto.setMoneda("ARS");
        prestamoDto.setPlazoMeses(12);

        clienteMock = new Cliente();
        clienteMock.setDni(42958843L);

        cuentaMock = new Cuenta();
        cuentaMock.setNumeroCuenta(1L);
        cuentaMock.setTipoMoneda(TipoMoneda.ARS); 
        cuentaMock.setSaldo(20000);

        prestamoMock = new Prestamo();
        prestamoMock.setId(1L);
        prestamoMock.setNumeroCliente(42958843L);
        prestamoMock.setMonto(10000);
        prestamoMock.setSaldoRestante(10000);
        prestamoMock.setPagosRealizados(0);
        prestamoMock.setMontoCuota(1000);
        prestamoMock.setMoneda("ARS");
    }


    @Test
    public void testSolicitarPrestamoSuccess() throws ClienteNoExistsException, CuentaNoEncontradaException, PrestamoException {

        when(clienteService.buscarClientePorDni(prestamoDto.getNumeroCliente())).thenReturn(clienteMock);
        when(cuentaService.obtenerCuentaParaPrestamo(prestamoDto.getNumeroCliente(), TipoMoneda.ARS)).thenReturn(cuentaMock);
        when(creditRatingService.tieneBuenHistorialCrediticio(prestamoDto.getNumeroCliente())).thenReturn(true);

        PrestamoResponseDto response = prestamoService.solicitarPrestamo(prestamoDto);

        // Aca verificamos que el prestamo fue guardado
        verify(prestamoDao, times(1)).save(any(Prestamo.class));

        assertEquals("APROBADO", response.getEstado());
        assertTrue(response.getMensaje().contains("El monto del préstamo fue acreditado"));
    }

    @Test
    public void testSolicitarPrestamoClienteNoExiste() {
        // Aca simulamos que no existe el cliente devolviendo un null
        when(clienteService.buscarClientePorDni(prestamoDto.getNumeroCliente())).thenReturn(null);

        // Verificamos que tire la excepcion correspondiente
        assertThrows(ClienteNoExistsException.class, () -> prestamoService.solicitarPrestamo(prestamoDto));
    }

    @Test
    public void testSolicitarPrestamo_HistorialCrediticioMalo() throws ClienteNoExistsException, CuentaNoEncontradaException {

        when(clienteService.buscarClientePorDni(prestamoDto.getNumeroCliente())).thenReturn(clienteMock);
        when(cuentaService.obtenerCuentaParaPrestamo(prestamoDto.getNumeroCliente(), TipoMoneda.ARS)).thenReturn(cuentaMock);

        // Aca simulamos que el cliente tiene un mal historial crediticio
        when(creditRatingService.tieneBuenHistorialCrediticio(prestamoDto.getNumeroCliente())).thenReturn(false);

        // Verificamos que tire la excepcion correspondiente
        assertThrows(PrestamoException.class, () -> prestamoService.solicitarPrestamo(prestamoDto));
    }


    @Test
    public void testObtenerPrestamosDeClienteSuccess() throws ClienteNoExistsException {

        when(clienteService.buscarClientePorDni(clienteMock.getDni())).thenReturn(clienteMock);
        List<Prestamo> prestamos = new ArrayList<>();
        prestamos.add(prestamoMock);
        when(prestamoDao.findAllByCliente(clienteMock.getDni())).thenReturn(prestamos);

        PrestamosClienteResponseDto response = prestamoService.obtenerPrestamosDeCliente(clienteMock.getDni());

        // Verificar los resultados
        assertEquals(1, response.getPrestamos().size());
        assertEquals(prestamoMock.getMonto(), response.getPrestamos().get(0).getMonto());
    }

    @Test
    public void testEjecutarPagoDeCuotaSuccess() throws PrestamoNoEncontradoException {
        // Configuración del pago de cuota
        PagoCuotaDto pagoCuotaDto = new PagoCuotaDto();
        pagoCuotaDto.setNumeroPrestamo(1L);
        pagoCuotaDto.setCantidadCuotas(1);
        pagoCuotaDto.setMontoPago(1000);

        // Simulamos un estado del prestamo donde se realizaron 5 pagos y el saldo restante es de 7000 con una cuota de 1000
        prestamoMock.setPlazoMeses(12); 
        prestamoMock.setPagosRealizados(5); 
        prestamoMock.setMontoCuota(1000); 
        prestamoMock.setSaldoRestante(7000);

        when(prestamoDao.find(pagoCuotaDto.getNumeroPrestamo())).thenReturn(prestamoMock);
        when(cuentaService.listCuentasByCliente(prestamoMock.getNumeroCliente())).thenReturn(List.of(cuentaMock));
        when(cuentaService.tieneSaldoDisponible(cuentaMock, 1000.0)).thenReturn(true);

        PagoCuotaResponseDto response = prestamoService.ejecutarPagoDeCuota(pagoCuotaDto);

        // Verificamos que se guardaron bien los cambios en el prestamo y la cuenta
        verify(prestamoDao, times(1)).save(prestamoMock);
        verify(cuentaService, times(1)).actualizarSaldo(cuentaMock.getNumeroCuenta(), cuentaMock.getSaldo() - 1000);
        assertEquals("COMPLETADO", response.getEstado());
    }



    @Test
    public void testEjecutarPagoDeCuotaPrestamoNoEncontrado() {
        PagoCuotaDto pagoCuotaDto = new PagoCuotaDto();
        pagoCuotaDto.setNumeroPrestamo(1L);

        // Aca simulamos que el prstamo no existe devolviendo un null
        when(prestamoDao.find(pagoCuotaDto.getNumeroPrestamo())).thenReturn(null);

        // Verificamos que tira la excepcion correspondiente
        assertThrows(PrestamoNoEncontradoException.class, () -> prestamoService.ejecutarPagoDeCuota(pagoCuotaDto));
    }
}
