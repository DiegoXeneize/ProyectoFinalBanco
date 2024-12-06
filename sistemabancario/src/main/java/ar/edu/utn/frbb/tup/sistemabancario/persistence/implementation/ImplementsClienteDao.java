package ar.edu.utn.frbb.tup.sistemabancario.persistence.implementation;


import ar.edu.utn.frbb.tup.sistemabancario.model.Cliente;
import ar.edu.utn.frbb.tup.sistemabancario.model.Cuenta;
import ar.edu.utn.frbb.tup.sistemabancario.persistence.AbstractDataBase;
import ar.edu.utn.frbb.tup.sistemabancario.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.sistemabancario.persistence.entity.ClienteEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ImplementsClienteDao extends AbstractDataBase implements ClienteDao {

    @Autowired
    ImplementsCuentaDao cuentaDao;

    @Override
    protected String getEntityName() {
        return "CLIENTE";
    }

    @Override
    public void save(Cliente cliente){
        ClienteEntity clienteEntity = new ClienteEntity(cliente);
        getInMemoryDataBase().put(clienteEntity.getId(), clienteEntity);
    }

    @Override
    public Cliente find(long dni, boolean cargarCuentas){
        if(getInMemoryDataBase().get(dni) == null){
            return null;
        }
        Cliente cliente = ((ClienteEntity) getInMemoryDataBase().get(dni)).toCliente();
        if(cargarCuentas){
            for(Cuenta c : cuentaDao.cuentasDelCliente(dni)){

                cliente.setCuentasDelCliente(c);
            }
        }

        return cliente;
    }

    public void update(Cliente cliente){
        ClienteEntity clienteEntity = new ClienteEntity(cliente);
        getInMemoryDataBase().put(clienteEntity.getId(), clienteEntity);

    }

    public void deleteCliente(long dni){
        getInMemoryDataBase().remove(dni);
    }

}
