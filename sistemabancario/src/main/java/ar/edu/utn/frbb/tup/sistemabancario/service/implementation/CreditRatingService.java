package ar.edu.utn.frbb.tup.sistemabancario.service.implementation;

import org.springframework.stereotype.Service;

@Service
public class CreditRatingService {
    public boolean tieneBuenHistorialCrediticio(long dni) {
        return dni % 2 != 0; // Retorna true si el DNI termina en impar (buen historial)
    }
}
