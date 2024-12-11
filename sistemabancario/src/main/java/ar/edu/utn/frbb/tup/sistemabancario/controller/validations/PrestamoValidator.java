package ar.edu.utn.frbb.tup.sistemabancario.controller.validations;

import ar.edu.utn.frbb.tup.sistemabancario.controller.dto.PrestamoDto;

public class PrestamoValidator {
    public void validatePrestamo(PrestamoDto prestamoDto) {

        String tipoMoneda = prestamoDto.getMoneda();
        tipoMoneda = tipoMoneda.toLowerCase();
        prestamoDto.setMoneda(tipoMoneda);

        if(!"pesos".equals(tipoMoneda) && !"dolares".equals(tipoMoneda)){
            throw new IllegalArgumentException("El tipo de moneda no es valido ('pesos' o 'dolares')");
        }

        if(prestamoDto.getPlazoMeses()<= 0){
            throw new IllegalArgumentException("El plazo de meses tiene que ser mayor a 0");
        }

        if(prestamoDto.getMontoPrestamo()<=0){
            throw new IllegalArgumentException("El monto del prestamo debe ser mayor a 0");
        }


    }
}
