package ar.edu.utn.frbb.tup.sistemabancario.persistence;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractDataBase {

    protected abstract String getEntityName();
    protected static Map<String, Map<Long, Object>> mapa = new HashMap<>();

    public Map<Long, Object> getInMemoryDataBase(){
        if(mapa.get(getEntityName()) == null){
            mapa.put(getEntityName(), new HashMap<>());
        }

        return mapa.get(getEntityName());
    }

}
