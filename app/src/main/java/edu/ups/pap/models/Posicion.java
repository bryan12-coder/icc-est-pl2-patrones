package edu.ups.pap.models;

public class Posicion {
    private int x;
    private int y;


    public Posicion(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public int getX() {
        return x;
    }


    public void setX(int x) {
        this.x = x;
    }


    public int getY() {
        return y;
    }


    public void setY(int y) {
        this.y = y;
    }

    public Posicion mover(Direccion direccion) {
        return switch (direccion) {
          case DERECHA -> new Posicion(x + 1, y);
           case IZQUIERDA -> new Posicion(x - 1, y);
           case ARRIBA -> new Posicion(x, y - 1);
          case ABAJO -> new Posicion(x, y + 1);
        };
    }

    
    
}
