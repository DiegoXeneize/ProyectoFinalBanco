package ar.edu.utn.frbb.tup.sistemabancario.persistence.implementation;

import ar.edu.utn.frbb.tup.sistemabancario.model.Movimientos;
import ar.edu.utn.frbb.tup.sistemabancario.persistence.AbstractDataBase;
import ar.edu.utn.frbb.tup.sistemabancario.persistence.TransferenciaDao;
import ar.edu.utn.frbb.tup.sistemabancario.persistence.entity.MovimientoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.List;

@Repository
public class ImplementsMovimientoDao extends AbstractDataBase implements TransferenciaDao {

    @Autowired
    ImplementsCuentaDao cuentaDao;

    @Override
    protected String getEntityName() {
        return "MOVIMIENTOS";
    }

    @Override
    public void save(Movimientos movimientos) {
        MovimientoEntity movimientoEntity = new MovimientoEntity(movimientos);
        getInMemoryDataBase().put(movimientoEntity.getId(), movimientoEntity);

    }

    @Override
    public List<Movimientos> findByNumeroCuenta(long numeroCuenta) {

        List<Movimientos> movimientosDeLaCuenta = new ArrayList<>();

        for(Object object : getInMemoryDataBase().values()){
            MovimientoEntity movimientos = (MovimientoEntity) object;

            if(movimientos.getNumeroCuenta() == numeroCuenta){
                movimientosDeLaCuenta.add(movimientos.toMovimiento());
            }
        }

        return movimientosDeLaCuenta;

    }
}
