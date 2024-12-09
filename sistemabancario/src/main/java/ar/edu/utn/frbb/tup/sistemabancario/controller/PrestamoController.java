package ar.edu.utn.frbb.tup.sistemabancario.controller;

import ar.edu.utn.frbb.tup.sistemabancario.controller.dto.PagoCuotaDto;
import ar.edu.utn.frbb.tup.sistemabancario.controller.dto.PagoCuotaResponseDto;
import ar.edu.utn.frbb.tup.sistemabancario.controller.dto.PrestamoDto;
import ar.edu.utn.frbb.tup.sistemabancario.controller.dto.PrestamoResponseDto;
import ar.edu.utn.frbb.tup.sistemabancario.controller.dto.PrestamosClienteResponseDto;
import ar.edu.utn.frbb.tup.sistemabancario.controller.validations.ValidationInput;
import ar.edu.utn.frbb.tup.sistemabancario.model.Prestamo;
import ar.edu.utn.frbb.tup.sistemabancario.model.exception.ClienteNoExistsException;
import ar.edu.utn.frbb.tup.sistemabancario.model.exception.CuentaNoEncontradaException;
import ar.edu.utn.frbb.tup.sistemabancario.model.exception.PrestamoException;
import ar.edu.utn.frbb.tup.sistemabancario.model.exception.PrestamoNoEncontradoException;
import ar.edu.utn.frbb.tup.sistemabancario.service.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prestamo")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    @Autowired
    ValidationInput validationInput;

    @PostMapping
    public ResponseEntity<PrestamoResponseDto> solicitarPrestamo(@RequestBody PrestamoDto prestamoDto) throws ClienteNoExistsException, CuentaNoEncontradaException, PrestamoException {
        return ResponseEntity.ok(prestamoService.solicitarPrestamo(prestamoDto));
    }

    @GetMapping("/{numeroCliente}")
    public ResponseEntity<PrestamosClienteResponseDto> obtenerPrestamos(@PathVariable long numeroCliente) {
        validationInput.validarDni(numeroCliente);
        return ResponseEntity.ok(prestamoService.obtenerPrestamosDeCliente(numeroCliente));
    }

    @PostMapping("/pagar")
    public ResponseEntity<PagoCuotaResponseDto> pagarCuota(@RequestBody PagoCuotaDto pagoCuotaDto) throws PrestamoNoEncontradoException {
        PagoCuotaResponseDto response = prestamoService.ejecutarPagoDeCuota(pagoCuotaDto);
        return ResponseEntity.ok(response);
}
}
