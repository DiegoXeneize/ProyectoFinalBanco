package ar.edu.utn.frbb.tup.sistemabancario.persistence.implementation;

import ar.edu.utn.frbb.tup.sistemabancario.model.Cuenta;
import ar.edu.utn.frbb.tup.sistemabancario.model.Movimientos;
import ar.edu.utn.frbb.tup.sistemabancario.persistence.AbstractDataBase;
import ar.edu.utn.frbb.tup.sistemabancario.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.sistemabancario.persistence.entity.CuentaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.List;

@Repository
public class ImplementsCuentaDao extends AbstractDataBase implements CuentaDao {

    @Autowired
    ImplementsMovimientoDao movimientoDao;

    @Override
    protected String getEntityName() {
        return "CUENTA";
    }

    @Override
    public Cuenta find(long id, boolean cargarMovimientos) {
        if(getInMemoryDataBase().get(id) == null){
            return null;
        }

        Cuenta cuenta = ((CuentaEntity) getInMemoryDataBase().get(id)).toCuenta();

        if(cargarMovimientos){
            for (Movimientos m : movimientoDao.findByNumeroCuenta(cuenta.getNumeroCuenta())){
                cuenta.addMovimientos(m);
            }
        }

        return cuenta;
    }

    @Override
    public void save(Cuenta cuenta) {
        CuentaEntity cuentaEntity = new CuentaEntity(cuenta);
        getInMemoryDataBase().put(cuenta.getNumeroCuenta(), cuentaEntity);
    }

    @Override
    public List<Cuenta> cuentasDelCliente(long dni) {
        List<Cuenta> cuentasCliente = new ArrayList<>();

        for (Object object : getInMemoryDataBase().values()){

            CuentaEntity cuentaEntity = (CuentaEntity) object;

            if(cuentaEntity.getDniTitutar().getDni() == dni){
                cuentasCliente.add(cuentaEntity.toCuenta());
            }
        }

        return cuentasCliente;
    }

    @Override
    public void updateSaldo(long numeroCuenta, double nuevoSaldo) {
        Cuenta cuenta = find(numeroCuenta, false);
        if (cuenta != null) {
            cuenta.setSaldo(nuevoSaldo);
            save(cuenta);
        }
    }

    public void update(Cuenta cuenta) {
        CuentaEntity cuentaEntity = new CuentaEntity(cuenta);
        getInMemoryDataBase().put(cuenta.getNumeroCuenta(), cuentaEntity);
    }
}
