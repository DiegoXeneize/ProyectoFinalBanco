package ar.edu.utn.frbb.tup.sistemabancario.controller;

import ar.edu.utn.frbb.tup.sistemabancario.controller.dto.CuentaDto;
import ar.edu.utn.frbb.tup.sistemabancario.controller.dto.DepositoDto;
import ar.edu.utn.frbb.tup.sistemabancario.controller.validations.ValidationInput;
import ar.edu.utn.frbb.tup.sistemabancario.model.Cuenta;
import ar.edu.utn.frbb.tup.sistemabancario.model.exception.*;
import ar.edu.utn.frbb.tup.sistemabancario.service.implementation.CuentaServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RestController
@RequestMapping("/api/cuenta")
public class CuentaController {

    @Autowired
    CuentaServiceImplementation cuentaService;

    @Autowired
    ValidationInput validationInput;

    @PostMapping("/agregar")
    public ResponseEntity<Object> addCuenta(@RequestBody CuentaDto cuentaDto, WebRequest request) throws TipoCuentaNotSupportedException, TipoCuentaAlreadyExistsException, CuentaAlreadyExistsException, ClienteNoExistsException {
        validationInput.validarDni(cuentaDto.getDniTitular());
        cuentaService.darAltaCuenta(cuentaDto, cuentaDto.getDniTitular());
        return ResponseEntity.status(HttpStatus.CREATED).body("Cuenta agregada con éxito");
    }

    @GetMapping("/all/{dniTitular}")
    public List<Cuenta> allCuentasByCliente(@PathVariable long dniTitular) throws CuentaNoEncontradaException {
        validationInput.validarDni(dniTitular);
        return cuentaService.listCuentasByCliente(dniTitular);
    }

    @PostMapping("/depositar")
    public ResponseEntity<Object> depositarDinero(@RequestBody DepositoDto depositoDto) throws CuentaNoEncontradaException {
        double saldoActual = cuentaService.depositar(depositoDto.getNumeroCuenta(), depositoDto.getMonto());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Depósito realizado con éxito. Saldo actual: " + saldoActual);
}
}
