package ar.edu.utn.frbb.tup.sistemabancario.persistence.implementation;

import ar.edu.utn.frbb.tup.sistemabancario.model.Prestamo;
import ar.edu.utn.frbb.tup.sistemabancario.persistence.AbstractDataBase;
import ar.edu.utn.frbb.tup.sistemabancario.persistence.PrestamoDao;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ImplementsPrestamoDao extends AbstractDataBase implements PrestamoDao {

    @Override
    protected String getEntityName() {
        return "PRESTAMO";
    }

    @Override
    public void save(Prestamo prestamo) {
        getInMemoryDataBase().put(prestamo.getId(), prestamo);
    }

    @Override
    public List<Prestamo> findAllByCliente(long numeroCliente) {
        List<Prestamo> prestamosCliente = new ArrayList<>();

        for (Object prestamo : getInMemoryDataBase().values()) {
            if (prestamo instanceof Prestamo && ((Prestamo) prestamo).getNumeroCliente() == numeroCliente) {
                prestamosCliente.add((Prestamo) prestamo);
            }
        }

        return prestamosCliente;
    }
}
