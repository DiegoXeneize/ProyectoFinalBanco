package ar.edu.utn.frbb.tup.sistemabancario.controller;

import ar.edu.utn.frbb.tup.sistemabancario.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.sistemabancario.controller.validations.ClienteValidator;
import ar.edu.utn.frbb.tup.sistemabancario.controller.validations.ValidationInput;
import ar.edu.utn.frbb.tup.sistemabancario.model.Cliente;
import ar.edu.utn.frbb.tup.sistemabancario.model.exception.ClienteAlreadyExistsException;
import ar.edu.utn.frbb.tup.sistemabancario.model.exception.ClienteNoExistsException;
import ar.edu.utn.frbb.tup.sistemabancario.model.exception.CuentaNoEncontradaException;
import ar.edu.utn.frbb.tup.sistemabancario.model.exception.MenorEdadException;
import ar.edu.utn.frbb.tup.sistemabancario.service.implementation.ClienteServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteServiceImplementation clienteService;

    @Autowired
    private ClienteValidator clienteValidator;

    @Autowired
    private ValidationInput validationInput;

    
    @PostMapping("/alta")
    public ResponseEntity<Object> crearCliente(@RequestBody ClienteDto clienteDto, WebRequest request) throws ClienteAlreadyExistsException, MenorEdadException {
        validationInput.validarInputCliente(clienteDto);
        clienteService.darAltaCliente(clienteDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Cliente creado con éxito");
    }

    @GetMapping("/{Id}")
    public Cliente buscarClientePorDni(@PathVariable long Id, WebRequest request) throws ClienteNoExistsException, MenorEdadException {
        validationInput.validarDni(Id);
        return clienteService.buscarClientePorDni(Id);
    }

    @PutMapping("/update/{dni}")
    public ResponseEntity<Object> updateCliente(@PathVariable long dni, @RequestBody ClienteDto clienteDto, WebRequest request) throws ClienteNoExistsException, MenorEdadException, CuentaNoEncontradaException {
        clienteService.updateCliente(clienteDto, dni);
        return  ResponseEntity.ok("Cliente modificado con exito");
    }

}
