package ar.edu.utn.frbb.tup.sistemabancario.model;


public enum TipoMoneda {
    ARS("ARS"),
    USD("USD");

    private final String text;

    TipoMoneda(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public static TipoMoneda fromString(String opcion){

        for(TipoMoneda tipo : TipoMoneda.values()){
            if(tipo.text.equalsIgnoreCase(opcion)){
                return tipo;
            }
        }

        throw new IllegalArgumentException("No se pudo encontrar un TipoMoneda con la descripción: " + opcion);

    }

}
