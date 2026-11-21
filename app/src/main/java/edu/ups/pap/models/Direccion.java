package edu.ups.pap.models;

public enum  Direccion {
    ARRIBA,
    ABAJO,
    DERECHA,
    IZQUIERDA;


    public boolean Opuesta(Direccion otra){
        return switch(this){
            case ARRIBA -> otra == ABAJO;
            case ABAJO -> otra == ARRIBA;
            case IZQUIERDA -> otra == DERECHA;
            case DERECHA -> otra == IZQUIERDA;

        };
    }


    
    
}
