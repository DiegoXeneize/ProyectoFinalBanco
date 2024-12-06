package ar.edu.utn.frbb.tup.sistemabancario.persistence.implementation;

import ar.edu.utn.frbb.tup.sistemabancario.model.Prestamo;
import ar.edu.utn.frbb.tup.sistemabancario.persistence.AbstractDataBase;
import ar.edu.utn.frbb.tup.sistemabancario.persistence.PrestamoDao;
import org.springframework.stereotype.Repository;

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
}
