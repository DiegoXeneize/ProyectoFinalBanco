package ar.edu.utn.frbb.tup.sistemabancario.controller;

import ar.edu.utn.frbb.tup.sistemabancario.controller.dto.PrestamoDto;
import ar.edu.utn.frbb.tup.sistemabancario.controller.validations.ValidationInput;
import ar.edu.utn.frbb.tup.sistemabancario.model.Prestamo;
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
    public ResponseEntity<Object> solicitarPrestamo(@RequestBody PrestamoDto prestamoDto) {
        try {
            Prestamo prestamo = prestamoService.solicitarPrestamo(prestamoDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(prestamo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{ClienteId}")
    public PrestamosClienteResponseDto ConsultaPrestamo(@PathVariable long ClienteId){
        return prestamoService.consultarPrestamo(ClienteId);
    } 

    @PutMapping("/pagar")
    public PrestamoResponseDto DebitarCuota(@RequestBody PagoCuotaPrestamoDto pagoCuotaPrestamoDto){
        cuotaPrestamoValidator.validate(pagoCuotaPrestamoDto);
        return prestamoService.pagarCuota(pagoCuotaPrestamoDto);
    }
    
}
