package ar.edu.utn.frbb.tup.sistemabancario.model;

import java.util.ArrayList;


public class Banco {
    private ArrayList<Cliente> clientes;
    private ArrayList<Cuenta> cuentas;

    public Banco(){
        this.clientes = new ArrayList<>();
        this.cuentas = new ArrayList<>();
    }


    public ArrayList<Cliente> getListaClientes() {
        return clientes;
    }

    public ArrayList<Cuenta> getListaCuentas() {
        return cuentas;
    }

    public void setListaClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }

    public void setListaCuentas(ArrayList<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }
}
